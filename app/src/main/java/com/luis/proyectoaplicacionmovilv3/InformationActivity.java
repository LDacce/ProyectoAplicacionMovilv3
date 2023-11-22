package com.luis.proyectoaplicacionmovilv3;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.luis.proyectoaplicacionmovilv3.api.OrderEventApiClient;
import com.luis.proyectoaplicacionmovilv3.models.OrderEventModel;
import com.luis.proyectoaplicacionmovilv3.adapters.OrderEventListAdapter;
import com.luis.proyectoaplicacionmovilv3.models.OrderModel;
import com.luis.proyectoaplicacionmovilv3.utils.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformationActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    public static OrderEventListAdapter orderEventsAdapter;
    OrderModel order;

    public static String ORDER_EXTRA = "ORDER_EXTRA";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        order = (OrderModel) getIntent().getSerializableExtra(ORDER_EXTRA);
        configToolbar();
        loadConsultantInfo();
        loadRecyclerView();
        orderEventsAdapter.setOnItemClickListener(new OrderEventListAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                OrderEventModel orderEvent = orderEventsAdapter.getOrderEvent(position);
                Intent intent = new Intent(InformationActivity.this, ManagementActivity.class);
                intent.putExtra(ManagementActivity.ORDER_ID_EXTRA, order.getId());
                intent.putExtra(ManagementActivity.ORDER_NUMBER_EXTRA, order.getOrderNumber());
                intent.putExtra(ManagementActivity.MANAGMENT_ACTION_EXTRA, "EDIT");
                intent.putExtra(ManagementActivity.ORDER_EVENT_ITEM_EXTRA, orderEvent);
                startActivity(intent);
            }
            @Override
            public void onDeleteClick(int position) {
                OrderEventModel orderEventModel = orderEventsAdapter.getOrderEvent(position);
                showOptionsDialog(orderEventModel, position);
            }
        });
        Button onCreateButton = findViewById(R.id.onCreateButton);
        onCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InformationActivity.this, ManagementActivity.class);
                intent.putExtra(ManagementActivity.ORDER_ID_EXTRA, order.getId());
                intent.putExtra(ManagementActivity.ORDER_NUMBER_EXTRA, order.getOrderNumber());
                intent.putExtra(ManagementActivity.MANAGMENT_ACTION_EXTRA, "CREATE");
                startActivity(intent);
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
        textView.setText("Informacion del Pedido N° " + order.getOrderNumber());
        textView.setTextSize(18);
        //textView.setTextColor(ContextCompat.getColor(this, R.color.md_text_color));
        textView.setGravity(Gravity.LEFT);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void loadConsultantInfo(){
        TextView consultantTextView = findViewById(R.id.consultant_edit);
        consultantTextView.setText(order.getConsultantName());
        TextView contactTextView = findViewById(R.id.contact_edit);
        contactTextView.setText(order.getConsultantPhone());
        TextView pieceTextView = findViewById(R.id.piece_edit);
        pieceTextView.setText(String.valueOf(order.getPieces()));
        TextView addressTextView = findViewById(R.id.address_edit);
        addressTextView.setText(order.getAddress());
    }
    private void loadRecyclerView(){
        recyclerView = findViewById(R.id.recycler_finalizados);
        orderEventsAdapter = new OrderEventListAdapter(order.getOrderevents());
        recyclerView.setAdapter(orderEventsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void showOptionsDialog(OrderEventModel orderEvent, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Está seguro de eliminar el evento?")
                .setMessage(orderEvent.getEvent().getDescription())
                .setPositiveButton("Sí, Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onDeleteOrder(orderEvent.getID(), position);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(), "Se canceló el proceso de eliminación",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
    private void onDeleteOrder(String id, int position) {
        OrderEventApiClient.OrderEventService service = OrderEventApiClient.getInstance().getService();
        service.deleteOrderEvent(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getBaseContext(),
                            "Se elimino el evento del pedido N°: " + order.getOrderNumber() + " " +
                                    "exitosamente",
                            Toast.LENGTH_SHORT).show();
                    orderEventsAdapter.removeOrderEvent(position);
                } else {
                    NetworkUtils.handleResponseError(getBaseContext(), response, "Error al " +
                            "eliminar el evento." +
                            " del pedido.", "DELETE_ORDER_EVENT" );
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                NetworkUtils.handleFailureError(getBaseContext(), t, "Error al " +
                        "eliminar el evento." +
                        " del pedido.", "DELETE_ORDER_EVENT" );
            }
        });
    }
}