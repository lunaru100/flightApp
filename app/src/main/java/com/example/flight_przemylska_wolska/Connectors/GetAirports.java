package com.example.flight_przemylska_wolska.Connectors;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetAirports extends AsyncTask<Void, Void, List<String>> {

    private final AutoCompleteTextView departureTextView;
    private final AutoCompleteTextView arrivalTextView;

    public GetAirports(AutoCompleteTextView departureTextView,AutoCompleteTextView arrivalTextView){
        this.departureTextView = departureTextView;
        this.arrivalTextView = arrivalTextView;
    }

    @Override
    protected List<String> doInBackground(Void... voids) {
        try {
            String serverUrl = "http://192.168.1.192:8080/airports/all"; // Endpoint do pobrania lotnisk
            URL url = new URL(serverUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }

                // Tutaj przetwórz JSON z listą lotnisk
                List<String> airports = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(response.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject airportJson = jsonArray.getJSONObject(i);
                    String airportName = airportJson.getString("name");
                    airports.add(airportName);
                }

                return airports;
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<String> airports) {
        // Tutaj obsłuż wynik - np. zaktualizuj widok AutoCompleteTextView w interfejsie użytkownika
        updateAutoCompleteTextView(departureTextView, airports);
        updateAutoCompleteTextView(arrivalTextView, airports);
    }

    private void updateAutoCompleteTextView(AutoCompleteTextView autoCompleteTextView, List<String> airports) {
        Collections.sort(airports);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(autoCompleteTextView.getContext(), android.R.layout.simple_dropdown_item_1line, airports);
        autoCompleteTextView.setAdapter(adapter);
    }
}