package com.example.fetchtakehome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends BaseAdapter {
    Context context;
    List<Data> data;
    public ListAdapter(Context context, List<Data>data){
        this.context = context;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View dataView = convertView;
        //if view needs to be created inflate cell layout
        if (dataView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            dataView = inflater.inflate(R.layout.jsondatacell, null);
        }
        // set the textViews in the cell to the appropriate information
        Data d = data.get(position);
        TextView textViewid = dataView.findViewById(R.id.dataIdTextView);
        textViewid.setText("Id: " + String.valueOf(d.getId()));
        TextView textViewListId = dataView.findViewById(R.id.listIdTextView);
        textViewListId.setText("ListId: " + String.valueOf(d.getListId()));

        TextView textViewName = dataView.findViewById(R.id.nameTextView);
        textViewName.setText("Name: " + d.getName());

        return dataView;
    }
}
