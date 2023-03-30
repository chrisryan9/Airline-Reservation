package com.airlinereservation.airlinereservation.entities;

import jakarta.persistence.*;

@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int flight_id;

    @ManyToOne
    @JoinColumn(name = "origin_airport_id", referencedColumnName = "airport_id")
    private Airport originAirport;

    @ManyToOne
    @JoinColumn(name = "destination_airport_id", referencedColumnName = "airport_id")
    private Airport destinationAirport;

    private String departure_time;
    private String arrival_time;
    private double price;

    public Flight() {
    }

    public Flight(int flight_id, Airport originAirport, Airport destinationAirport, String departure_time, String arrival_time, double price) {
        this.flight_id = flight_id;
        this.originAirport = originAirport;
        this.destinationAirport = destinationAirport;
        this.departure_time = departure_time;
        this.arrival_time = arrival_time;
        this.price = price;
    }

    public int getFlight_id() {
        return flight_id;
    }

    public int getId() {
        return flight_id;
    }

    public void setFlight_id(int flight_id) {
        this.flight_id = flight_id;
    }

    public Airport getOriginAirport() {
        return originAirport;
    }

    public void setOriginAirport(Airport originAirport) {
        this.originAirport = originAirport;
    }

    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(Airport destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Object getDepartureAirport() {
        return null;
    }

    public Object getArrivalAirport() {
        return null;
    }

    public String getDepartureTime() {
        return null;
    }

    public String getArrivalTime() {
        return null;
    }
}
