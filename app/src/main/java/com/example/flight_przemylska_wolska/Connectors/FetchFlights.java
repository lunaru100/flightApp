package com.example.flight_przemylska_wolska.Connectors;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.flight_przemylska_wolska.Adapters.FlightAdapter;
import com.example.flight_przemylska_wolska.MainActivity;
import com.example.flight_przemylska_wolska.Models.AirportModel;
import com.example.flight_przemylska_wolska.Models.FlightModel;
import com.example.flight_przemylska_wolska.Models.PlaneModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class FetchFlights extends AsyncTask<String, Void, List<FlightModel>> {
    private final MainActivity mainActivity;

    public FetchFlights(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected List<FlightModel> doInBackground(String... strings) {
        try {
            String destination = strings[0];
            String arrival = strings[1];

            String serverUrl = "http://192.168.1.192:8080/android/getFlightsDestArr";
            URL url = new URL(serverUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // Set up the request
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setDoOutput(true);

            // Create JSON payload
            JSONObject jsonPayload = new JSONObject();
            jsonPayload.put("dest", destination);
            jsonPayload.put("arrival", arrival);

            // Write JSON payload to the request body
            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(jsonPayload.toString());
            writer.flush();
            writer.close();
            outputStream.close();

            // Get the response
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            String response = convertStreamToString(inputStream);

            // Parse the JSON response and return the list of flights
            return parseFlightResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<FlightModel> flights) {
        if (flights != null) {
            // Update your UI with the list of flights
            FlightAdapter flightAdapter = new FlightAdapter(mainActivity, flights);
            mainActivity.setFlightListViewAdapter(flightAdapter);
        } else {
            Toast.makeText(mainActivity, "Error fetching flights", Toast.LENGTH_SHORT).show();
        }
    }


    // Helper method to convert InputStream to String
    private String convertStreamToString(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    // Helper method to parse JSON response
    private List<FlightModel> parseFlightResponse(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            List<FlightModel> flightList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject flightJson = jsonArray.getJSONObject(i);
                // Parse flight details and create Flight objects
                // Update this part based on your Flight model
                FlightModel flight = new FlightModel();
                // Set properties from the JSON response
                flight.setId(flightJson.getLong("id"));

                // Example for nested objects (assuming AirportModel and PlaneModel have similar parsing logic)
                AirportModel departureAirport = new AirportModel();
                departureAirport.setName(flightJson.getJSONObject("departure").getString("name"));
                // Set other properties of departureAirport
                flight.setDeparture(departureAirport);

                AirportModel arrivalAirport = new AirportModel();
                arrivalAirport.setName(flightJson.getJSONObject("arrival").getString("name"));
                // Set other properties of arrivalAirport
                flight.setArrival(arrivalAirport);

                PlaneModel plane = new PlaneModel();
                plane.setId(flightJson.getJSONObject("plane").getLong("id"));
                // Set other properties of plane
                flight.setPlane(plane);

                flight.setDepartureDate(flightJson.getString("departureDate"));
                flight.setArrivalDate(flightJson.getString("arrivalDate"));

                // Add the FlightModel to the list
                flightList.add(flight);
            }

            return flightList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
