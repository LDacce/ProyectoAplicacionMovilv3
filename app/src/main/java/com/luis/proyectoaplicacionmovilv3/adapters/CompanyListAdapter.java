package com.luis.proyectoaplicacionmovilv3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.luis.proyectoaplicacionmovilv3.models.CompanyModel;
import com.luis.proyectoaplicacionmovilv3.models.CompanyModel;

import java.util.List;


public class CompanyListAdapter extends ArrayAdapter<CompanyModel> {
    private List<CompanyModel> companyList;

    public CompanyListAdapter(Context context, int resource, List<CompanyModel> companyList) {
        super(context, resource, companyList);
        this.companyList = companyList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.select_dialog_item, parent,
                    false);
        }
        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setText(companyList.get(position).getDescription());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}


