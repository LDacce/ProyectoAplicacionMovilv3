package com.luis.proyectoaplicacionmovilv3.api;


import com.luis.proyectoaplicacionmovilv3.models.CompanyModel;
import com.luis.proyectoaplicacionmovilv3.models.EventModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MasterApiClient {


    public interface MasterService {
        @GET("masters/listEvents")
        Call<List<EventModel>> getEventList();

        @GET("masters/listCompanies")
        Call<List<CompanyModel>> getCompanyList();
    }

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://transolyback-production.up.railway.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private MasterService service;

    private static final MasterApiClient ourInstance = new MasterApiClient();
    public static MasterApiClient getInstance() {
        return ourInstance;
    }
    private MasterApiClient() {
        // Constructor privado para evitar la creación de instancias fuera de la clase
    }
    public MasterService getService(){
        if (service == null) {
            service = retrofit.create(MasterService.class);
        }
        return service;
    }
}

