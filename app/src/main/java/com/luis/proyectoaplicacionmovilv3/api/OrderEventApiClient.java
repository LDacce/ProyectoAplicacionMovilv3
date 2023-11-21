package com.luis.proyectoaplicacionmovilv3.api;

import com.luis.proyectoaplicacionmovilv3.dtos.CreateOrderEventDto;
import com.luis.proyectoaplicacionmovilv3.dtos.UpdateOrderEventDto;
import com.luis.proyectoaplicacionmovilv3.models.OrderEventModel;
import com.luis.proyectoaplicacionmovilv3.models.OrderModel;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class OrderEventApiClient {
    public interface OrderEventService {
        @POST("order-events")
        Call<OrderEventModel> createOrderEvent(@Body() CreateOrderEventDto dto);
        @PATCH("order-events/{id}")
        Call<OrderEventModel> updateOrderEvent(@Path("id") String id, @Body UpdateOrderEventDto dto);
        @DELETE("order-events/{id}")
        Call<Void> deleteOrderEvent(@Path("id") String id);
        @Multipart
        @POST("order-events/upload/{orderEventId}")
        Call<OrderEventModel> uploadEventImages(
                @Path("orderEventId") String orderEventId,
                @Part MultipartBody.Part mainImage,
                @Part MultipartBody.Part referenceImage
        );
    }

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://transolyback-production.up.railway.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private OrderEventApiClient.OrderEventService service;

    private static final OrderEventApiClient ourInstance = new OrderEventApiClient();
    public static OrderEventApiClient getInstance() {
        return ourInstance;
    }
    private OrderEventApiClient() {
        // Constructor privado para evitar la creación de instancias fuera de la clase
    }
    public OrderEventApiClient.OrderEventService getService(){
        if (service == null) {
            service = retrofit.create(OrderEventApiClient.OrderEventService.class);
        }
        return service;
    }
}




