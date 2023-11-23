package com.luis.proyectoaplicacionmovilv3.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luis.proyectoaplicacionmovilv3.R;
import com.luis.proyectoaplicacionmovilv3.models.OrderEventModel;
import com.luis.proyectoaplicacionmovilv3.utils.DateUtil;

import java.io.Serializable;
import java.util.List;

public class OrderEventListAdapter extends RecyclerView.Adapter<OrderEventListAdapter.ViewHolder> implements Serializable {
    List<OrderEventModel> orderEventList;
    public OrderEventListAdapter(List<OrderEventModel> orderEventList){
        this.orderEventList = orderEventList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_order_events, parent,false);
        return new ViewHolder(vista);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderEventModel orderEventModel = orderEventList.get(position);

        holder.createDate_text.setText(DateUtil.parseISODate(orderEventModel.getCreateDate().toString()));
        holder.event_text.setText(orderEventModel.getEvent().getDescription().toUpperCase());

        int eventId = orderEventModel.getEvent().getId();
        if (eventId == 1 || eventId == 2) {
            holder.editButton.setVisibility(View.GONE);
            holder.deleteButton.setVisibility(View.GONE);
        } else {
            holder.editButton.setVisibility(View.VISIBLE);
            holder.deleteButton.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public int getItemCount() {
        return orderEventList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView createDate_text, event_text;
        ImageView editButton, deleteButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            createDate_text = itemView.findViewById(R.id.createDate_text);
            event_text = itemView.findViewById(R.id.event_text);
            editButton = itemView.findViewById(R.id.onEditButton);
            deleteButton = itemView.findViewById(R.id.onDeleteButton);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onEditClick(position);
                        }
                    }
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }
    public OrderEventModel getOrderEvent(int position) {
        return orderEventList.get(position);
    }
    public void addOrderEvent(OrderEventModel orderEvent) {
        orderEventList.add(0, orderEvent);
        notifyDataSetChanged();
    }
    public void editOrderEvent(int position, OrderEventModel updatedOrderEvent) {
        orderEventList.set(position, updatedOrderEvent);
        notifyItemChanged(position);
    }
    public void removeOrderEvent(int position) {
        orderEventList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, orderEventList.size());
    }
    public int getPosition(String orderEventId) {
        for (int i = 0; i < orderEventList.size(); i++) {
            if (orderEventList.get(i).getID().equals(orderEventId)) {
                return i;
            }
        }
        return -1; // Si no se encuentra el evento, devuelve -1
    }
    public interface OnItemClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }
    private OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
