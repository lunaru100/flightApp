package com.example.flight_przemylska_wolska;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flight_przemylska_wolska.Adapters.FlightAdapter;
import com.example.flight_przemylska_wolska.Connectors.FetchFlights;
import com.example.flight_przemylska_wolska.Connectors.GetAirports;
import com.example.flight_przemylska_wolska.Models.FlightModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView flightListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AutoCompleteTextView departureTextView = findViewById(R.id.departure);
        AutoCompleteTextView arrivalTextView = findViewById(R.id.arrival);

        flightListView = findViewById(R.id.flightListView);

        View.OnClickListener dropDownClickListener = v -> {
            AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) v;
            autoCompleteTextView.showDropDown();
        };

        departureTextView.setOnClickListener(dropDownClickListener);
        arrivalTextView.setOnClickListener(dropDownClickListener);

        new GetAirports(departureTextView, arrivalTextView).execute();

        Button searchBtn = findViewById(R.id.search);
        searchBtn.setOnClickListener(view -> {
            String departureValue = departureTextView.getText().toString();
            String arrivalValue = arrivalTextView.getText().toString();

            if (departureValue.equals(arrivalValue)) {
                Toast.makeText(this, "Departure and arrival must be different", Toast.LENGTH_SHORT).show();
            } else {
                // Execute FetchFlights AsyncTask with departure and arrival values
                new FetchFlights(MainActivity.this) {
                    @Override
                    protected void onPostExecute(List<FlightModel> flights) {
                        if (flights != null) {
                            // Update your UI with the list of flights
                            FlightAdapter flightAdapter = new FlightAdapter(MainActivity.this, flights);
                            setFlightListViewAdapter(flightAdapter);
                        } else {
                            Toast.makeText(MainActivity.this, "Error fetching flights", Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute(departureValue, arrivalValue);
            }
        });
    }

    public void setFlightListViewAdapter(FlightAdapter flightAdapter) {
        flightListView.setAdapter(flightAdapter);
    }
}