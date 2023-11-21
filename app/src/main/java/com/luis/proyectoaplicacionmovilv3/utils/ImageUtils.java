package com.luis.proyectoaplicacionmovilv3.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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

    public static MultipartBody.Part convertUriToMultipartBodyPart(Uri uriDeLaImagen,
                                                                  String nombreDelCampo) {
        File archivo = new File(uriDeLaImagen.getPath());

        RequestBody solicitudDeArchivo = RequestBody.create(
                MediaType.parse("image/*"),
                archivo
        );

        return MultipartBody.Part.createFormData(nombreDelCampo, archivo.getName(), solicitudDeArchivo);
    }

    public static Uri convertDrawableToUri(Context context, Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

        File file = new File(context.getCacheDir(), "temp_image.png");
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Uri.fromFile(file);
    }
}
