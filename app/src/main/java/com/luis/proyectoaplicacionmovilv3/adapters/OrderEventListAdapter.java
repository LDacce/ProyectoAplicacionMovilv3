package com.luis.proyectoaplicacionmovilv3.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luis.proyectoaplicacionmovilv3.R;
import com.luis.proyectoaplicacionmovilv3.models.OrderEventModel;

import java.util.List;

//djijwiejweiwjeiwjeweiwjiewjiejwiewjie
public class OrderEventListAdapter extends RecyclerView.Adapter<OrderEventListAdapter.ViewHolder> {

    List<OrderEventModel> listaOrderModels;

    public OrderEventListAdapter(List<OrderEventModel> listaOrderModels){
        this.listaOrderModels = listaOrderModels;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_order_events, parent,false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderEventModel orderEventModel = listaOrderModels.get(position);

        holder.createDate_text.setText(orderEventModel.getCreateDate().toString());//VERIFICAR EL
        // FORMATO DE LA FECHA
        holder.event_text.setText(orderEventModel.getEvent().getDescription().toUpperCase());

    }

    @Override
    public int getItemCount() {
        return listaOrderModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView createDate_text, event_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            createDate_text = itemView.findViewById(R.id.createDate_text);
            event_text = itemView.findViewById(R.id.event_text);

        }
    }
}
