package com.luis.proyectoaplicacionmovilv3.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ImageUtils {
    public static String drawableToBase64(Drawable drawable) {
        // Convertir el drawable a un Bitmap
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

        // Convertir el bitmap a un array de bytes
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        // Convertir el array de bytes a Base64
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
