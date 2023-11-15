package com.luis.proyectoaplicacionmovilv3.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luis.proyectoaplicacionmovilv3.R;
import com.luis.proyectoaplicacionmovilv3.models.OrderModel;

import java.util.List;

//djijwiejweiwjeiwjeweiwjiewjiejwiewjie
public class OrderEventListAdapter extends RecyclerView.Adapter<OrderEventListAdapter.ViewHolder> {

    List<OrderModel> listaOrderModels;

    public OrderEventListAdapter(List<OrderModel> listaOrderModels){
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
        OrderModel orderModel = listaOrderModels.get(position);

        holder.nroPedido_text.setText(orderModel.nroPedido.toUpperCase());
        holder.evento_text.setText(orderModel.eventPedido.toUpperCase());



    }

    @Override
    public int getItemCount() {
        return listaOrderModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nroPedido_text, evento_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nroPedido_text = itemView.findViewById(R.id.NroPedido_text);
            evento_text = itemView.findViewById(R.id.Evento_text);

        }
    }
}
