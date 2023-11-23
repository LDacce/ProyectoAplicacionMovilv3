package com.luis.proyectoaplicacionmovilv3.utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {
    public static String parseISODate(String isoDate) {
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy, hh:mm a", Locale.getDefault());

        try {
            isoFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // Establecer la zona horaria UTC
            Date date = isoFormat.parse(isoDate);
            outputFormat.setTimeZone(TimeZone.getDefault()); // Establecer la zona horaria predeterminada del dispositivo
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Invalid Date";
        }
    }
}
