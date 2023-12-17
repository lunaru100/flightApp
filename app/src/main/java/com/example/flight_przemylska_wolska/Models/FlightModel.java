package com.example.flight_przemylska_wolska.Models;

public class FlightModel {
    private long id;
    private AirportModel departure;
    private AirportModel arrival;
    private PlaneModel plane;
    private String departureDate;
    private String arrivalDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AirportModel getDeparture() {
        return departure;
    }

    public void setDeparture(AirportModel departure) {
        this.departure = departure;
    }

    public AirportModel getArrival() {
        return arrival;
    }

    public void setArrival(AirportModel arrival) {
        this.arrival = arrival;
    }

    public PlaneModel getPlane() {
        return plane;
    }

    public void setPlane(PlaneModel plane) {
        this.plane = plane;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
}
