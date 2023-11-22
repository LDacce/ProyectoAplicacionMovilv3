package com.luis.proyectoaplicacionmovilv3;

import static com.luis.proyectoaplicacionmovilv3.utils.ImageUtils.*;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
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
import com.luis.proyectoaplicacionmovilv3.adapters.OrderEventListAdapter;
import com.luis.proyectoaplicacionmovilv3.api.MasterApiClient;

import com.luis.proyectoaplicacionmovilv3.api.OrderEventApiClient;
import com.luis.proyectoaplicacionmovilv3.dtos.CreateOrderEventDto;
import com.luis.proyectoaplicacionmovilv3.dtos.UpdateOrderEventDto;
import com.luis.proyectoaplicacionmovilv3.fragments.CameraFragment;
import com.luis.proyectoaplicacionmovilv3.models.EventModel;
import com.luis.proyectoaplicacionmovilv3.models.OrderEventModel;
import com.luis.proyectoaplicacionmovilv3.utils.ImageUtils;
import com.luis.proyectoaplicacionmovilv3.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagementActivity extends AppCompatActivity {
    private String orderId;
    private String orderNumber;
    private String managmentAction;

    private OrderEventModel orderEvent;

    Spinner eventsComboBox;
    EditText observationsTextArea;
    private CameraFragment mainCameraFragment;
    private CameraFragment refCameraFragment;

    public static String ORDER_ID_EXTRA = "ORDER_ID_EXTRA";
    public static String ORDER_NUMBER_EXTRA = "ORDER_NUMBER_EXTRA";
    public static String MANAGMENT_ACTION_EXTRA = "MANAGMENT_ACTION_EXTRA";
    public static String ORDER_EVENT_ITEM_EXTRA = "ORDER_EVENT_ITEM_EXTRA";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managment);
        orderId = (String) getIntent().getSerializableExtra(ORDER_ID_EXTRA);
        orderNumber = (String) getIntent().getSerializableExtra(ORDER_NUMBER_EXTRA);
        managmentAction = (String) getIntent().getSerializableExtra(MANAGMENT_ACTION_EXTRA);
        orderEvent = (OrderEventModel) getIntent().getSerializableExtra(
                ORDER_EVENT_ITEM_EXTRA);
        configToolbar();
        configCameraFragments();

        eventsComboBox = findViewById(R.id.eventsComboBox);
        observationsTextArea = findViewById(R.id.observationTextArea);

        eventsComboBox.setAdapter(MainActivity.eventListAdapter);

        if (orderEvent != null && managmentAction.equals("EDIT")) {
            eventsComboBox.setSelection(MainActivity.eventListAdapter.getPosition(orderEvent.getEvent().getId()),
                    true);
            observationsTextArea.setText(orderEvent.getObservations());
            if (orderEvent.getMainImageURL() != null && !orderEvent.getMainImageURL().equals("")) {
                mainCameraFragment.setUriInImageButtonLoad(Uri.parse(orderEvent.getMainImageURL()));
            }
            if (orderEvent.getReferenceImageURL() != null && !orderEvent.getReferenceImageURL().equals("")) {
                refCameraFragment.setUriInImageButtonLoad(Uri.parse(orderEvent.getReferenceImageURL()));
            }
        }
        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (managmentAction.equals("CREATE")) {
                    if (isFormValid()) {
                        onCreateOrder();
                    }
                }
                if (managmentAction.equals("EDIT")) {
                    if (isFormDirty()){
                        if (isFormValid())
                        {
                            onEditOrder();
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "No se realizo ningún cambio",
                                Toast.LENGTH_SHORT).show();
                    }

                }
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
        if (managmentAction.equals("CREATE")) {
            textView.setText("Registro de Evento del Pedido N° "+orderNumber);
        }
        if (managmentAction.equals("EDIT")) {
            textView.setText("Actualización de Evento del Pedido N° "+orderNumber);
        }
        textView.setTextSize(18);
        //textView.setTextColor(ContextCompat.getColor(this, R.color.md_text_color));
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

    private boolean isFormValid() {
        String selectedEvent = eventsComboBox.getSelectedItem().toString();
        if (TextUtils.isEmpty(selectedEvent)) {
            Toast.makeText(getBaseContext(), "Ingrese un Evento",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        String observations = observationsTextArea.getText().toString();
        if (TextUtils.isEmpty(observations)){
            Toast.makeText(getBaseContext(), "Ingrese las Observaciones",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
            if (mainCameraFragment.getUri() == null){
                Toast.makeText(getBaseContext(), "Ingrese una Foto de Cargo",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
            if (refCameraFragment.getUri() == null){
                Toast.makeText(getBaseContext(), "Ingrese una Foto de Referencia",
                        Toast.LENGTH_SHORT).show();
                return false;
            }


        return true;
    }
    private boolean isFormDirty(){
        return eventsComboBox.isDirty() || observationsTextArea.isDirty() || mainCameraFragment.isImageButtonLoadDirty || refCameraFragment.isImageButtonLoadDirty;
    }
    public void onCreateOrder() {
        EventModel selectedEvent = (EventModel) eventsComboBox.getSelectedItem();
        String observationText = observationsTextArea.getText().toString();
        CreateOrderEventDto dto = new CreateOrderEventDto(selectedEvent.getId(), "af6d3b8f-832d-4ae6-ab06-e83fe0a1b1c5", orderId,
                observationText, "", "",
                "", "");

        OrderEventApiClient.OrderEventService service = OrderEventApiClient.getInstance().getService();
        service.createOrderEvent(dto).enqueue(new Callback<OrderEventModel>() {
            @Override
            public void onResponse(Call<OrderEventModel> call, Response<OrderEventModel> response) {
                if (response.isSuccessful()) {
                    OrderEventModel orderEvent = response.body();
                    InformationActivity.orderEventsAdapter.addOrderEvent(orderEvent);
                    uploadFiles(orderEvent.getID());
                } else {
                    NetworkUtils.handleResponseError(getBaseContext(), response, "Error al crear " +
                            "el evento del pedido", "CREATE_ORDER_EVENT");
                }
            }
            @Override
            public void onFailure(Call<OrderEventModel> call, Throwable t) {
                NetworkUtils.handleFailureError(getBaseContext(), t, "Error al crear " +
                        "el evento del pedido", "CREATE_ORDER_EVENT");
            }
        });
    }
    public void onEditOrder() {
        EventModel selectedEvent = (EventModel) eventsComboBox.getSelectedItem();
        String observationText = observationsTextArea.getText().toString();
        UpdateOrderEventDto dto = new UpdateOrderEventDto(selectedEvent.getId(),
                orderEvent.getUser().getID(),
                orderId,
                observationText, orderEvent.getMainImageURL(), orderEvent.getReferenceImageURL(),
                "", "");

        OrderEventApiClient.OrderEventService service = OrderEventApiClient.getInstance().getService();
        service.updateOrderEvent(orderEvent.getID(), dto).enqueue(new Callback<OrderEventModel>() {
            @Override
            public void onResponse(Call<OrderEventModel> call, Response<OrderEventModel> response) {
                if (response.isSuccessful()) {
                    OrderEventModel orderEvent = response.body();
                    int position =
                            InformationActivity.orderEventsAdapter.getPosition(orderEvent.getID());
                    InformationActivity.orderEventsAdapter.editOrderEvent(position, orderEvent);
                    uploadFiles(orderEvent.getID());
                } else {
                    NetworkUtils.handleResponseError(getBaseContext(), response, "Error al editar el evento del pedido", "EDIT_ORDER_EVENT");
                }
            }
            @Override
            public void onFailure(Call<OrderEventModel> call, Throwable t) {
                NetworkUtils.handleFailureError(getBaseContext(), t, "Error al editar el evento " +
                        "del pedido", "EDIT_ORDER_EVENT");
            }
        });
    }

    private void uploadFiles(String orderEventId){
        OrderEventApiClient.OrderEventService service = OrderEventApiClient.getInstance().getService();
        MultipartBody.Part mainImgPart = convertUriToMultipartBodyPart(mainCameraFragment.getUri(), "mainImage");

        MultipartBody.Part refImgPart = convertUriToMultipartBodyPart(refCameraFragment.getUri(), "referenceImage");

        Call<OrderEventModel> call = service.uploadEventImages(orderEventId, mainImgPart, refImgPart);
        call.enqueue(new Callback<OrderEventModel>() {
            @Override
            public void onResponse(Call<OrderEventModel> call, Response<OrderEventModel> response) {
                if (response.isSuccessful()) {
                    OrderEventModel orderEvent = response.body();
                    int position =
                            InformationActivity.orderEventsAdapter.getPosition(orderEvent.getID());
                    InformationActivity.orderEventsAdapter.editOrderEvent(position, orderEvent);
                    Toast.makeText(getBaseContext(), "Se registro el pedido exitosamente",
                            Toast.LENGTH_SHORT).show();
                } else {
                    NetworkUtils.handleResponseError(getBaseContext(), response, "Se registro el pedido exitosamente, pero Hubo un Error al subir las imagenes, actualicelas.", "UPLOAD_IMAGES");
                }
                finish();
            }
            @Override
            public void onFailure(Call<OrderEventModel> call, Throwable t) {
                NetworkUtils.handleFailureError(getBaseContext(), t, "Se registro el pedido " +
                        "exitosamente, pero Hubo un Error al subir las imagenes, actualicelas.", "UPLOAD_IMAGES");
                finish();
            }
        });
    }
}