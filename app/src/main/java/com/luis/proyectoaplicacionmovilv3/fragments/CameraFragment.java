package com.luis.proyectoaplicacionmovilv3.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.luis.proyectoaplicacionmovilv3.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraFragment extends Fragment {
    private View view;
    private Bitmap bitmap;
    private ImageButton imageButtonBase;
    private ImageButton imageButtonLoad;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public boolean isImageButtonLoadDirty = false;

    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<Intent> galleryLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_camera, container, false);

        Bundle args = getArguments();
        if (args != null) {
            String defaultText = args.getString("defaultText");
            TextView textView = view.findViewById(R.id.title_text);
            textView.setText(defaultText);
        }

        imageButtonBase = view.findViewById(R.id.imagen_button_base);
        imageButtonLoad = view.findViewById(R.id.imagen_button_load);
        imageButtonLoad.setVisibility(View.GONE);

        imageButtonBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionsDialog();
            }
        });

        imageButtonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionsDialog();
            }
        });

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Bundle extras = result.getData().getExtras();
                            Bitmap imageBitmap = (Bitmap) extras.get("data");
                            if (imageBitmap != null){
                                setBitmapInImageButtonLoad(imageBitmap);
                                isImageButtonLoadDirty = true;
                            } else {
                                Toast.makeText(requireContext(), "Error al obtener la imagen", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(requireContext(), "Proceso Cancelado por el Usuario", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Uri uri = result.getData().getData();
                            if (uri != null) {
                                try {
                                    bitmap =
                                            MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), uri);
                                    setBitmapInImageButtonLoad(bitmap);
                                    isImageButtonLoadDirty = true;
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            } else {
                                Toast.makeText(requireContext(), "Error al obtener la URI de la imagen", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(requireContext(), "Proceso Cancelado por el Usuario", Toast.LENGTH_SHORT).show();
                        }
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
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Selecciona una opción")
                .setMessage("¿Cómo deseas cargar la imagen?")
                .setPositiveButton("Abrir Cámara", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (checkCameraPermission()) {
                            Log.e("PERMISSION_CAMERA", "TIENE PERMISOS LA CAMARA");
                            openCamera();
                        } else {
                            Log.e("PERMISSION_CAMERA", "NO TIENE PERMISOS LA CAMARA");
                            requestCameraPermission();
                        }
                    }
                })
                .setNegativeButton("Ir a Galería", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (checkGalleryPermission()) {
                            openGallery();
                        } else {
                            requestGalleryPermission();
                        }
                    }
                })
                .show();
    }
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getContext().getPackageManager()) != null) {
            cameraLauncher.launch(cameraIntent);
        }
    }
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        galleryLauncher.launch(galleryIntent);
    }
    public void setBitmapInImageButtonLoad(Bitmap _bitmap) {
        if (imageButtonLoad != null) {
            bitmap = _bitmap;
            imageButtonLoad.setImageBitmap(bitmap);
            imageButtonLoad.setVisibility(View.VISIBLE);
            imageButtonBase.setVisibility(View.GONE);}
        else {
            // Realizar acciones adicionales si el ImageButton es nulo
        }

    }
    private ActivityResultLauncher<String> cameraPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), result -> {
                if (result) {
                    openCamera();
                } else {
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        showCameraPermissionDeniedMessage();
                    }
                }
            });

    private ActivityResultLauncher<String> galleryPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), result -> {
                if (result) {
                    openGallery();
                } else {
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        showGalleryPermissionDeniedMessage();
                    }
                }
            });

    private boolean checkCameraPermission() {
        int cameraPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA);
        return cameraPermission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        String cameraPermission = Manifest.permission.CAMERA;
        cameraPermissionLauncher.launch(cameraPermission);
    }

    private boolean checkGalleryPermission() {
        int readPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        return readPermission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestGalleryPermission() {
        String galleryPermission = Manifest.permission.READ_EXTERNAL_STORAGE;
        galleryPermissionLauncher.launch(galleryPermission);
    }
    private void showCameraPermissionDeniedMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Permiso denegado")
                .setMessage("El permiso de la cámara se ha denegado con anterioridad. No es " +
                        "posible capturar imágenes. Por favor, habilite el permiso desde la configuración de la aplicación.")
                .setPositiveButton("Configuración", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openAppSettings();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
    private void showGalleryPermissionDeniedMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Permiso denegado")
                .setMessage("El permiso de la galería se ha denegado con anterioridad. No es " +
                        "posible seleccionar imágenes. Por favor, habilite el permiso desde la configuración de la aplicación.")
                .setPositiveButton("Configuración", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openAppSettings();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
    private void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", requireContext().getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }
}