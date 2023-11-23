package com.luis.proyectoaplicacionmovilv3;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.luis.proyectoaplicacionmovilv3.adapters.CompanyListAdapter;
import com.luis.proyectoaplicacionmovilv3.adapters.EventListAdapter;
import com.luis.proyectoaplicacionmovilv3.api.MasterApiClient;
import com.luis.proyectoaplicacionmovilv3.api.OrderApiClient;
import com.luis.proyectoaplicacionmovilv3.databinding.ActivityMainBinding;
import com.luis.proyectoaplicacionmovilv3.fragments.BarChartFragment;
import com.luis.proyectoaplicacionmovilv3.fragments.CameraFragment;
import com.luis.proyectoaplicacionmovilv3.models.CompanyModel;
import com.luis.proyectoaplicacionmovilv3.models.EventModel;
import com.luis.proyectoaplicacionmovilv3.models.OrderModel;
import com.luis.proyectoaplicacionmovilv3.utils.NetworkUtil;
import com.luis.proyectoaplicacionmovilv3.utils.ProgressDialogUtil;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    public static EventListAdapter eventListAdapter;
    public static CompanyListAdapter companyListAdapter;
    private ActivityResultLauncher<ScanOptions> barcodeLauncher;
    ScanOptions scanOptions;
    private BarChartFragment barChartFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadCompanies();
        loadEvents();
        configBarChartFragment();


        Button onSubmitButton = findViewById(R.id.onSubmit);
        Button onScanButton = findViewById(R.id.scanButton);

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
        onScanButton.setOnClickListener(v -> {
            initScan();
        });
        barcodeLauncher = registerForActivityResult(new ScanContract(), result -> {
            if (result.getContents() == null){
                Toast.makeText(this, "Proceso del Scan Cancelado", Toast.LENGTH_SHORT).show();
            } else {
                binding.orderNumberEditText.setText(result.getContents());
            }
        });
    }
    private void configBarChartFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        barChartFragment = BarChartFragment.newInstance("Charts");
        fragmentTransaction.add(R.id.fragmentBarchatContainer, barChartFragment);
        fragmentTransaction.commit();
    }
    private void getOrder(String orderNumber, int companyId){
        ProgressDialogUtil.showProgressDialog(MainActivity.this, "Obteniendo Pedido...");
        OrderApiClient.OrderService orderService = OrderApiClient.getInstance().getService();
        Call<OrderModel> call = orderService.getOrder("",
                orderNumber,
                companyId);
        call.enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                ProgressDialogUtil.dismissProgressDialog();
                if (response.isSuccessful()) {
                    OrderModel order = response.body();
                    Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                    intent.putExtra(InformationActivity.ORDER_EXTRA, order);
                    startActivity(intent);
                } else {
                    NetworkUtil.handleResponseError(MainActivity.this, response);
                }
            }
            @Override
            public void onFailure(Call<OrderModel> call, Throwable t) {
                ProgressDialogUtil.dismissProgressDialog();
                NetworkUtil.handleFailureError(MainActivity.this, t);
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
                    NetworkUtil.handleResponseError(MainActivity.this, response);
                }
            }
            @Override
            public void onFailure(Call<List<CompanyModel>> call, Throwable t) {
                NetworkUtil.handleFailureError(MainActivity.this, t);
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
                    NetworkUtil.handleResponseError(MainActivity.this, response);
                }
            }
            @Override
            public void onFailure(Call<List<EventModel>> call, Throwable t) {
                NetworkUtil.handleFailureError(MainActivity.this, t);
            }
        });
    }

    private void initScan(){
        if (scanOptions == null) {
            scanOptions = new ScanOptions();
            scanOptions.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES);
            scanOptions.setPrompt("Scanee el Codigo del Pedido");
            scanOptions.setCameraId(0);
            scanOptions.setOrientationLocked(false);
            scanOptions.setBeepEnabled(true);
            scanOptions.setCaptureActivity(CaptureActivityPortraint.class);
            scanOptions.setBarcodeImageEnabled(false);
        }
        barcodeLauncher.launch(scanOptions);
    }
}