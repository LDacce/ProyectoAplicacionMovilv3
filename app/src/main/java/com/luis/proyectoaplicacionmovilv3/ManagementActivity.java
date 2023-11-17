package com.luis.proyectoaplicacionmovilv3;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.luis.proyectoaplicacionmovilv3.adapters.EventListAdapter;
import com.luis.proyectoaplicacionmovilv3.api.MasterApiClient;

import com.luis.proyectoaplicacionmovilv3.models.EventModel;
import com.luis.proyectoaplicacionmovilv3.models.OrderModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagementActivity extends AppCompatActivity {
    private String orderId;
    private String orderNumber;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managment);

        String orderId = (String) getIntent().getSerializableExtra("ORDER_ID_EXTRA");
        String orderNumber = (String) getIntent().getSerializableExtra("ORDER_NUMBER_EXTRA");

        configToolbar();
        loadEvents();


        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitButton();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void loadEvents() {
        Spinner eventsComboBox = (Spinner) findViewById(R.id.eventsComboBox);
        // Obtener instancia de MasterService
        MasterApiClient.MasterService service = MasterApiClient.getInstance().getService();
        // Realizar la llamada asíncrona
        service.getEventList().enqueue(new Callback<List<EventModel>>() {
            @Override
            public void onResponse(Call<List<EventModel>> call, Response<List<EventModel>> response) {
                if (response.isSuccessful()) {
                    // La llamada fue exitosa, establecer la respuesta en el spinner
                    List<EventModel> eventList = response.body();
                    EventListAdapter adapter = new EventListAdapter(getBaseContext(),
                            android.R.layout.simple_spinner_item,eventList); //
                    // Utiliza tu EventAdapter personalizado
                    eventsComboBox.setAdapter(adapter);
                } else {
                    // La llamada no fue exitosa, mostrar un mensaje de error
                    Toast.makeText(getBaseContext(), "Error al obtener la lista de eventos",
                            Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<EventModel>> call, Throwable t) {
                // Error en la llamada, mostrar un mensaje de error
                Toast.makeText(getBaseContext(), "Error al realizar la llamada", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onBackView(View view) {
        // Aquí colocas la lógica que deseas ejecutar cuando se haga clic en el botón
    }
    public void onSubmitButton() {
        // Obtener el ComboBox
        Spinner eventsComboBox = findViewById(R.id.eventsComboBox);
        // Obtener el texto seleccionado en el ComboBox
        EventModel selectedEvent = (EventModel) eventsComboBox.getSelectedItem();
        // Obtener el TextArea
        EditText observationTextArea = findViewById(R.id.observationTextArea);
        // Obtener el texto ingresado en el TextArea
        String observationText = observationTextArea.getText().toString();
        // Obtener las imágenes de los botones
        ImageView mainImageButton = findViewById(R.id.mainImageButton);
        ImageView referenceImageButton =  findViewById(R.id.referenceImageButton);

        Log.d("eventId", String.valueOf(selectedEvent.getId()));
        Log.d("observation", observationText);
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
}