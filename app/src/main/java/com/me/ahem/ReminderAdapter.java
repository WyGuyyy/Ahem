package com.me.ahem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ReminderAdapter extends BaseAdapter {

    Context context;
    List<RowItem> data;
    private static LayoutInflater inflater = null;

    public ReminderAdapter(Context context, List<RowItem> data){
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<RowItem> data) {
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = inflater.inflate(R.layout.row, null);

            TextView name = (TextView) view.findViewById(R.id.rowName);
            TextView distance = (TextView) view.findViewById(R.id.rowDistance);
            TextView longitude = (TextView) view.findViewById(R.id.rowLong);
            TextView latitude = (TextView) view.findViewById(R.id.rowLat);
            TextView address = (TextView) view.findViewById(R.id.rowAddress);

            name.setText(data.get(position).getName());
            distance.setText(String.valueOf(data.get(position).getRadius()));
            longitude.setText(String.valueOf(data.get(position).getLongitude()));
            latitude.setText(String.valueOf(data.get(position).getLatitude()));
            address.setText(data.get(position).getAddress());

            return view;
        }

        return view;
    }
}
