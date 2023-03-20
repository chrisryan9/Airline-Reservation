package com.airlinereservation.airlinereservation.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int airport_id;
    private String airport_code;
    private String airport_name;
    private String airport_city;
    private String airport_country;


    public Airport() {
    }

    public Airport(String airport_code, String airport_name, String airport_city, String airport_country) {
        this.airport_code = airport_code;
        this.airport_name = airport_name;
        this.airport_city = airport_city;
        this.airport_country = airport_country;
    }

    public Airport(Object departureAirport) {
    }

    public int getAirport_id() {
        return airport_id;
    }

    public void setAirport_id(int airport_id) {
        this.airport_id = airport_id;
    }

    public String getAirport_name() {
        return airport_name;
    }

    public void setAirport_name(String airport_name) {
        this.airport_name = airport_name;
    }

    public String getAirport_city() {
        return airport_city;
    }

    public void setAirport_city(String airport_city) {
        this.airport_city = airport_city;
    }

    public String getAirport_country() {
        return airport_country;
    }

    public void setAirport_country(String airport_country) {
        this.airport_country = airport_country;
    }

    public String getAirport_code() {
        return airport_code;
    }

    public void setAirport_code(String airport_code) {
        this.airport_code = airport_code;
    }
}
