package com.luis.proyectoaplicacionmovilv3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.luis.proyectoaplicacionmovilv3.api.OrderApiClient;
import com.luis.proyectoaplicacionmovilv3.models.OrderModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.submit_button);
        btn.setOnClickListener(v -> {
            //Se obtiene el numero de Pedido(orderNumber) y el id de la compañia(companyId)
            /*........*/
            //Se inicializa el servicio del OrderApiClient
            OrderApiClient.OrderService orderService = OrderApiClient.getInstance().getService();
            // Se llama al método getOrder pasandole el orderNumber y companyId
            Call<OrderModel> call = orderService.getOrder("", "77777777", 3);
            call.enqueue(new Callback<OrderModel>() {
                @Override
                public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                    if (response.isSuccessful()) {
                        OrderModel order = response.body();
                        //depuracion
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        String json = gson.toJson(order);
                        Log.d("RESPONSE_JSON", json);
                        //Aqui se tiene que pasar el pedido(order) al Intent
                        Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                        intent.putExtra("ORDER_EXTRA", order);
                        startActivity(intent);
                    } else {
                        Log.d("err", "error ok");
                        // Manejar el error(Ej. Mostrar un toast con el error)
                    }
                }
                @Override
                public void onFailure(Call<OrderModel> call, Throwable t) {
                    Log.e("err", t.toString());
                    // Manejar el fallo en la solicitud(Ej. Mostrar un toast con el error)
                }
            });


        });
    }
}