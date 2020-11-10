package com.vwwsapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vwwsapp.activities.R;
import com.vwwsapp.helpers.DateType;
import com.vwwsapp.helpers.FitIn;
import com.vwwsapp.model.MeterReaderAssignments;
import com.vwwsapp.model.Reading;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ReadingAdapter extends ArrayAdapter<MeterReaderAssignments> {
    public ReadingAdapter(Context context, ArrayList<MeterReaderAssignments> readings) {
        super(context, 0, readings);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MeterReaderAssignments reading = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_consumer, parent, false);
        }
        // Lookup view for data population
        TextView meter_no = (TextView) convertView.findViewById(R.id.meter_no);
        TextView customer_name = (TextView) convertView.findViewById(R.id.customer_name);

        ImageView img = (ImageView) convertView.findViewById(R.id.imageView);
        // Populate the data into the template view using the data object
        meter_no.setText(reading.getMeter_no());
        customer_name.setText(reading.getCustomer_name());
        if (reading.getStatus() == 0) {
            img.setImageResource(R.drawable.cross1);
        } else {
            img.setImageResource(R.drawable.checked3);
        }



        // Return the completed view to render on screen
        return convertView;
    }

}
