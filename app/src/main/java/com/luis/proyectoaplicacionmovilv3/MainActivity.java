package com.luis.proyectoaplicacionmovilv3;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.luis.proyectoaplicacionmovilv3.adapters.CompanyListAdapter;
import com.luis.proyectoaplicacionmovilv3.adapters.EventListAdapter;
import com.luis.proyectoaplicacionmovilv3.api.MasterApiClient;
import com.luis.proyectoaplicacionmovilv3.api.OrderApiClient;
import com.luis.proyectoaplicacionmovilv3.databinding.ActivityMainBinding;
import com.luis.proyectoaplicacionmovilv3.models.CompanyModel;
import com.luis.proyectoaplicacionmovilv3.models.EventModel;
import com.luis.proyectoaplicacionmovilv3.models.OrderModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    public static EventListAdapter eventListAdapter;
    public static CompanyListAdapter companyListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadCompanies();
        loadEvents();

        Button onSubmitButton = findViewById(R.id.onSubmit);
        onSubmitButton.setOnClickListener( v -> {
            Spinner companiesSpinner = findViewById(R.id.companySpinner);
            CompanyModel companySelected = (CompanyModel) companiesSpinner.getSelectedItem();
            int companyId = companySelected.getId();
            if (companyId == 0) {
                Toast.makeText(getBaseContext(), "Seleccione una Empresa",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            String orderNumber = String.valueOf(binding.orderNumberEditText.getText());
            if (orderNumber.equals("")) {
                Toast.makeText(getBaseContext(), "Ingrese un Nro de Pedido",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            getOrder(orderNumber, companyId);
        });
    }

    private void getOrder(String orderNumber, int companyId){
        OrderApiClient.OrderService orderService = OrderApiClient.getInstance().getService();
        Call<OrderModel> call = orderService.getOrder("",
                orderNumber,
                companyId);
        call.enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                if (response.isSuccessful()) {
                    OrderModel order = response.body();
                    Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                    intent.putExtra(InformationActivity.ORDER_EXTRA, order);
                    startActivity(intent);
                } else {
                    Toast.makeText(getBaseContext(), "Sucedio un error al obtener el Pedido",
                            Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<OrderModel> call, Throwable t) {
                Toast.makeText(getBaseContext(),
                        "Sucedio un error al obtener el Pedido. ERROR: " +t.getMessage(),
                        Toast.LENGTH_SHORT).show();
                Log.e("GET_ORDER_ERROR", t.toString());
            }
        });
    }

    private void loadCompanies() {
        Spinner companySpinner = (Spinner) findViewById(R.id.companySpinner);
        MasterApiClient.MasterService service = MasterApiClient.getInstance().getService();
        service.getCompanyList().enqueue(new Callback<List<CompanyModel>>() {
            @Override
            public void onResponse(Call<List<CompanyModel>> call, Response<List<CompanyModel>> response) {
                if (response.isSuccessful()) {
                    List<CompanyModel> companyList = response.body();
                    companyListAdapter = new CompanyListAdapter(getBaseContext(),
                            android.R.layout.select_dialog_item,companyList);
                    companySpinner.setAdapter(companyListAdapter);
                } else {
                    Toast.makeText(getBaseContext(), "Error al obtener la lista de empresas",
                            Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<CompanyModel>> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Error al obtener la lista de empresas",
                        Toast.LENGTH_SHORT).show();
                Log.e("GET_COMPANY_LIST_ERROR", t.toString());

            }
        });
    }
    private void loadEvents() {
        MasterApiClient.MasterService service = MasterApiClient.getInstance().getService();
        service.getEventList().enqueue(new Callback<List<EventModel>>() {
            @Override
            public void onResponse(Call<List<EventModel>> call, Response<List<EventModel>> response) {
                if (response.isSuccessful()) {
                    List<EventModel> eventList = response.body();
                    eventListAdapter = new EventListAdapter(getBaseContext(),
                            android.R.layout.select_dialog_item,eventList); //
                } else {
                    Toast.makeText(getBaseContext(), "Error al obtener la lista de eventos",
                            Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<EventModel>> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Error al obtener la lista de eventos", Toast.LENGTH_SHORT).show();
                Log.e("GET_EVENT_LIST_ERROR", t.toString());
            }
        });
    }
}