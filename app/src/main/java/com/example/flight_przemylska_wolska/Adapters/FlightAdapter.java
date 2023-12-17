package com.example.flight_przemylska_wolska.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.flight_przemylska_wolska.Models.FlightModel;
import com.example.flight_przemylska_wolska.R;

import java.util.List;

public class FlightAdapter extends BaseAdapter {

    private Context context;
    private List<FlightModel> flightList;

    public FlightAdapter(Context context, List<FlightModel> flightList) {
        this.context = context;
        this.flightList = flightList;
    }

    @Override
    public int getCount() {
        return flightList.size();
    }

    @Override
    public Object getItem(int position) {
        return flightList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the layout for each list item
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.flight_list_item, parent, false);
        }

        // Get the current FlightModel object
        FlightModel flight = flightList.get(position);

        // Set the data to the views within the list item layout
        TextView departureTextView = convertView.findViewById(R.id.departureTextView);
        departureTextView.setText("Departure: " + flight.getDeparture().getName());

        TextView arrivalTextView = convertView.findViewById(R.id.arrivalTextView);
        arrivalTextView.setText("Arrival: " + flight.getArrival().getName());

        TextView planeIdTextView = convertView.findViewById(R.id.planeIdTextView);
        planeIdTextView.setText("Plane ID: " + flight.getPlane().getId());

        // Add more TextViews or customize as needed

        return convertView;
    }
}
