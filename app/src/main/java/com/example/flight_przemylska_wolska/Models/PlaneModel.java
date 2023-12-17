package com.example.flight_przemylska_wolska.Models;

public class PlaneModel {
    private long id;
    private String model;
    private int seatsCount;
    private String brand;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getSeatsCount() {
        return seatsCount;
    }

    public void setSeatsCount(int seatsCount) {
        this.seatsCount = seatsCount;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
