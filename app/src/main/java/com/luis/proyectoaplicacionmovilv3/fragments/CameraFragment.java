package com.luis.proyectoaplicacionmovilv3.fragments;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.luis.proyectoaplicacionmovilv3.ManagementActivity;
import com.luis.proyectoaplicacionmovilv3.R;

public class CameraFragment extends Fragment {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private View view;
    private Uri uri;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_camera, container,
                false);

        Bundle args = getArguments();
        if (args != null) {
            String defaultText = args.getString("defaultText");
            TextView textView = view.findViewById(R.id.title_text);
            textView.setText(defaultText);
        }

        // Obtener referencia al botón
        ImageButton button = view.findViewById(R.id.imagen_button);

        // Configurar el clic del botón para abrir la cámara
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionsDialog();
            }
        });

        return view;
    }

    public static CameraFragment newInstance(String defaultText) {
        CameraFragment fragment = new CameraFragment();
        Bundle args = new Bundle();
        args.putString("defaultText", defaultText);
        fragment.setArguments(args);
        return fragment;
    }

    private void showOptionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Selecciona una opción");
        builder.setItems(new CharSequence[]{"Abrir Cámara", "Ir a Galería"},
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Aquí puedes manejar la opción seleccionada
                        switch (which) {
                            case 0:
                                // Código para abrir la cámara
                                openCamera();
                                break;
                            case 1:
                                // Código para abrir la galería
                                openGallery();
                                break;
                        }
                    }
                });
        builder.show();
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Título");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Descripción");

        uri =
                getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        cameraARL.launch(intent);

    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryARL.launch(intent);
    }
    private ActivityResultLauncher<Intent> galleryARL = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        uri = data.getData();
                        ImageButton button = view.findViewById(R.id.imagen_button);
                        button.setImageURI(uri);
                    } else {
                        Toast.makeText(getContext(), "Proceso Cancelado por el Usuario",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private ActivityResultLauncher<Intent> cameraARL = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        ImageButton button = view.findViewById(R.id.imagen_button);
                        button.setImageURI(uri);
                    } else {
                        Toast.makeText(getContext(), "Proceso Cancelado por el Usuario",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

}

