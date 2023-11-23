package com.luis.proyectoaplicacionmovilv3.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.luis.proyectoaplicacionmovilv3.models.HttpErrorResponseCustom;

import retrofit2.Response;
import java.io.IOException;

public class NetworkUtil {
    public static <T> void handleResponseError(Activity activity, Response<T> response) {
        try {
            String errorBody = response.errorBody().string();
            Gson gson = new Gson();
            HttpErrorResponseCustom responseCustom = gson.fromJson(errorBody,
                    HttpErrorResponseCustom.class);
            Log.e("EXCEPTION", responseCustom.getMessage());
            showPopup(activity, responseCustom);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("EXCEPTION", e.getMessage());
        }
    }

    public static void handleFailureError(Activity activity, Throwable t) {
        String errorMessage = t.getMessage();
        Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show();
        Log.e("ERROR_API", errorMessage);
    }

    private static void showPopup(Activity activity, HttpErrorResponseCustom errorResponseCustom) {
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(errorResponseCustom.getError() + " " + errorResponseCustom.getStatusCode())
                    .setMessage(errorResponseCustom.getMessage())
                    .setPositiveButton("Aceptar", null)
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            // Realizar acciones adicionales al cerrar el diálogo, si es necesario
                        }
                    })
                    .show();
        }
    }
}