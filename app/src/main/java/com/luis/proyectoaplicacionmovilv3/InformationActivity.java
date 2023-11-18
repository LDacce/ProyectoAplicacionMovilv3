package com.luis.proyectoaplicacionmovilv3;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luis.proyectoaplicacionmovilv3.models.EventModel;
import com.luis.proyectoaplicacionmovilv3.models.OrderEventModel;
import com.luis.proyectoaplicacionmovilv3.adapters.OrderEventListAdapter;
import com.luis.proyectoaplicacionmovilv3.models.OrderModel;
import com.luis.proyectoaplicacionmovilv3.models.UserModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InformationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<OrderEventModel> pList;
    OrderEventListAdapter orderEventsAdapter;
    //
    List<OrderEventModel> listaOrderModel = new ArrayList<>();
    OrderEventListAdapter orderEventListAdapter = new OrderEventListAdapter(listaOrderModel);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        configToolbar();
        /*
        *
        *
        *
        * {
        "createDate": "2023-11-16T17:21:51.032Z",
        "updateDate": null,
        "id": "738414fd-825b-4640-b0bb-704ac5a7db41",
        "observations": "",
        "mainImageUrl": "",
        "referenceImageUrl": "",
        "longitude": "",
        "latitude": "",
        "__event__": {
          "id": 1,
          "description": "En Almacén"
        },
        "__user__": null
      },
      {
        "createDate": "2023-11-16T17:21:51.027Z",
        "updateDate": null,
        "id": "d19aeb95-8b1d-4bec-b05f-079a6c1db742",
        "observations": "",
        "mainImageUrl": "",
        "referenceImageUrl": "",
        "longitude": "",
        "latitude": "",
        "__event__": {
          "id": 2,
          "description": "En envio"
        },
        "__user__": null
      }*/
        //Obtener la data pasada por el Intent
        OrderModel order = (OrderModel) getIntent().getSerializableExtra("ORDER_EXTRA");

        pList = new ArrayList<>();
        pList.add(new OrderEventModel("738414fd-825b-4640-b0bb-704ac5a7db41", "", "", "", "", "",
                new EventModel(1, "En Almacen"), new UserModel("738414fd-825b-4640-b0bb-704ac5a7db4", "admin"),
                "2023-11-16T17:21:51.032Z", ""));
        pList.add(new OrderEventModel("d19aeb95-8b1d-4bec-b05f-079a6c1db742", "", "", "", "", "",
                new EventModel(2, "En envio"), new UserModel("738414fd-825b-4640-b0bb" +
                "-704ac5a7db4", "admin"),
                "2023-11-16T17:21:51.027Z", ""));


        recyclerView = findViewById(R.id.recycler_finalizados);
        orderEventsAdapter = new OrderEventListAdapter(pList);
        recyclerView.setAdapter(orderEventsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Button btn = findViewById(R.id.onCreateButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InformationActivity.this, ManagementActivity.class);
                intent.putExtra("ORDER_ID_EXTRA", order.getID());
                intent.putExtra("ORDER_NUMBER_EXTRA", order.getOrderNumber());
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
        textView.setText("Informacion del Pedido N° xxxxxx");
        textView.setTextSize(18);
        textView.setTextColor(ContextCompat.getColor(this, R.color.md_text_color));
        textView.setGravity(Gravity.LEFT);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}