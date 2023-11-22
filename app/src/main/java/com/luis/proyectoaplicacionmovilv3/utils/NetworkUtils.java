package com.luis.proyectoaplicacionmovilv3.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import retrofit2.Response;
import java.io.IOException;

public class NetworkUtils {
    public static <T> void handleResponseError(Context context, Response<T> response,
                                               String errorCustomMessage, String method) {
        try {
            String errorBody = response.errorBody().string();
            Toast.makeText(context, errorCustomMessage + " Error: " + errorBody, Toast.LENGTH_SHORT).show();
            Log.e(method + "_ERROR", errorBody.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(method + "_ERROR", e.getMessage());
        }
    }
    public static <T> void handleFailureError(Context context, Throwable t,
                                               String errorCustomMessage, String method) {
        String errorMessage = t.getMessage();
        Toast.makeText(context, errorCustomMessage +
                        " ERROR: " + errorMessage,
                Toast.LENGTH_SHORT).show();
        Log.e(method + "_ERROR", errorMessage.toString());
    }
}