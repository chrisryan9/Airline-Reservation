package com.airlinereservation.airlinereservation.services;

import com.airlinereservation.airlinereservation.entities.Flight;

import java.util.List;

public class FlightResponse {
    private List<Flight> data;

    public FlightResponse() {
    }

    public FlightResponse(List<Flight> data) {
        this.data = data;
    }

    public List<Flight> getData() {
        return data;
    }

    public void setData(List<Flight> data) {
        this.data = data;
    }
}
