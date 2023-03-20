package com.airlinereservation.airlinereservation.entities;


import jakarta.persistence.*;
import com.airlinereservation.airlinereservation.entities.User;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservation_id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "flight_id", referencedColumnName = "flight_id")
    private Flight flight;

    private String reservation_status;
    private String payment_status;
    private double price;

    public Reservation() {
    }

    public Reservation(int reservation_id, User user, Flight flight, String reservation_status, String payment_status, double price) {
        this.reservation_id = reservation_id;
        this.user = user;
        this.flight = flight;
        this.reservation_status = reservation_status;
        this.payment_status = payment_status;
        this.price = price;
    }

    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getReservation_status() {
        return reservation_status;
    }

    public void setReservation_status(String reservation_status) {
        this.reservation_status = reservation_status;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
