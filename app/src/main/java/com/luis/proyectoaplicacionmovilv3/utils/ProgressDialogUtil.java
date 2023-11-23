package com.luis.proyectoaplicacionmovilv3.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.ProgressBar;

import com.luis.proyectoaplicacionmovilv3.R;

public class ProgressDialogUtil {

    private static Dialog progressDialog;

    public static void showProgressDialog(Context context, String message) {
        progressDialog = new Dialog(context);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.custom_progress_dialog);
        progressDialog.setCancelable(false);

        ProgressBar progressBar = progressDialog.findViewById(R.id.progressBar);
        // Customize the progress bar if desired

        progressDialog.show();
    }

    public static void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
