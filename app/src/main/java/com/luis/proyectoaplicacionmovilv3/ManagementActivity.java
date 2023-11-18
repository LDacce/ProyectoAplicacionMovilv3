package com.luis.proyectoaplicacionmovilv3;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.luis.proyectoaplicacionmovilv3.adapters.EventListAdapter;
import com.luis.proyectoaplicacionmovilv3.api.MasterApiClient;

import com.luis.proyectoaplicacionmovilv3.fragments.CameraFragment;
import com.luis.proyectoaplicacionmovilv3.models.EventModel;
import com.luis.proyectoaplicacionmovilv3.utils.ImageUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagementActivity extends AppCompatActivity {
    private String orderId;
    private String orderNumber;

    private CameraFragment mainCameraFragment;
    private CameraFragment refCameraFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managment);

        orderId = (String) getIntent().getSerializableExtra("ORDER_ID_EXTRA");
        orderNumber = (String) getIntent().getSerializableExtra("ORDER_NUMBER_EXTRA");

        configToolbar();
        configCameraFragments();
        loadEvents();


        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateOrder();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    private void configToolbar() {
        assert getSupportActionBar() != null;
        TextView textView = new TextView(this);
        textView.setText("Registro/Actualizacion de Evento del Pedido N° "+orderNumber);
        textView.setTextSize(18);
        textView.setTextColor(ContextCompat.getColor(this, R.color.md_text_color));
        textView.setGravity(Gravity.LEFT);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void configCameraFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Crear una nueva instancia de CameraFragment con texto predeterminado
        mainCameraFragment = CameraFragment.newInstance("Foto de Cargo");
        fragmentTransaction.add(R.id.fragment_container_main, mainCameraFragment);

        // Crear una nueva instancia de CameraFragment con texto predeterminado
        refCameraFragment = CameraFragment.newInstance("Foto Referencial");
        fragmentTransaction.add(R.id.fragment_container_ref, refCameraFragment);

        fragmentTransaction.commit();
    }

    private void loadEvents() {
        Spinner eventsComboBox = (Spinner) findViewById(R.id.eventsComboBox);
        MasterApiClient.MasterService service = MasterApiClient.getInstance().getService();
        service.getEventList().enqueue(new Callback<List<EventModel>>() {
            @Override
            public void onResponse(Call<List<EventModel>> call, Response<List<EventModel>> response) {
                if (response.isSuccessful()) {
                    List<EventModel> eventList = response.body();
                    EventListAdapter adapter = new EventListAdapter(getBaseContext(),
                            android.R.layout.simple_spinner_item,eventList); //
                    eventsComboBox.setAdapter(adapter);
                } else {
                    Toast.makeText(getBaseContext(), "Error al obtener la lista de eventos",
                            Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<EventModel>> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Error al realizar la llamada", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onCreateOrder() {
        Spinner eventsComboBox = findViewById(R.id.eventsComboBox);
        EventModel selectedEvent = (EventModel) eventsComboBox.getSelectedItem();

        EditText observationTextArea = findViewById(R.id.observationTextArea);

        String observationText = observationTextArea.getText().toString();

        ImageView mainImageButton = mainCameraFragment.getView().findViewById(R.id.imagen_button);
        ImageView referenceImageButton =  refCameraFragment.getView().findViewById(R.id.imagen_button);


        String mainImgBase64 = ImageUtils.drawableToBase64(mainImageButton.getDrawable());
        String refImgBase64 = ImageUtils.drawableToBase64(referenceImageButton.getDrawable());

        Log.d("eventId", String.valueOf(selectedEvent.getId()));
        Log.d("observation", observationText);
    }
}