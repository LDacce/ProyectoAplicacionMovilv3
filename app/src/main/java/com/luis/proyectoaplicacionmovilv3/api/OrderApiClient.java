package com.luis.proyectoaplicacionmovilv3.api;

import com.luis.proyectoaplicacionmovilv3.models.CompanyModel;
import com.luis.proyectoaplicacionmovilv3.models.EventModel;
import com.luis.proyectoaplicacionmovilv3.models.OrderModel;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class OrderApiClient {
    public interface OrderService {
        @Headers("Cache-Control: no-cache")
        @GET("orders/getOne")
        Call<OrderModel> getOrder(@Query("id") String id,
                                  @Query("orderNumber") String orderNumber,
                                  @Query("companyId") int companyId);
    }
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://transolyback-production.up.railway.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private OrderApiClient.OrderService service;

    private static final OrderApiClient ourInstance = new OrderApiClient();
    public static OrderApiClient getInstance() {
        return ourInstance;
    }
    private OrderApiClient() {
        // Constructor privado para evitar la creación de instancias fuera de la clase
    }
    public OrderApiClient.OrderService getService(){
        if (service == null) {
            service = retrofit.create(OrderApiClient.OrderService.class);
        }
        return service;
    }
}
