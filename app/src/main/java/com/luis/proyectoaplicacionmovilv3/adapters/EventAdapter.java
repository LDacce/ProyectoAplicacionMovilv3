package com.luis.proyectoaplicacionmovilv3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.luis.proyectoaplicacionmovilv3.models.EventModel;

import java.util.List;


public class EventAdapter extends ArrayAdapter<EventModel> {
    private List<EventModel> eventList;

    public EventAdapter(Context context, int resource, List<EventModel> eventList) {
        super(context, resource, eventList);
        this.eventList = eventList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setText(eventList.get(position).getDescription());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}


