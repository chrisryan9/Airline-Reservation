package com.airlinereservation.airlinereservation.services;

import com.airlinereservation.airlinereservation.entities.Flight;
import com.airlinereservation.airlinereservation.entities.Reservation;
import com.airlinereservation.airlinereservation.entities.User;
import com.airlinereservation.airlinereservation.repositories.ReservationRepository;
import com.airlinereservation.airlinereservation.repositories.FlightRepository;
import com.airlinereservation.airlinereservation.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private UserRepository userRepository;

    public Reservation saveReservation(Reservation reservation) {
        // First, save the User instance if it's not already persisted
        User user = reservation.getUser();
        if (user != null && user.getId() == null) {
            user = userRepository.save(user);
            reservation.setUser(user);
        }

        // Save the Flight instance if it's not already persisted
        Flight flight = reservation.getFlight();
        if (flight != null && flight.getId() == 0) {
            flight = flightRepository.save(flight);
            reservation.setFlight(flight);
        }

        // Then, save the Reservation instance
        return reservationRepository.save(reservation);
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> findReservationById(Integer id) {
        return reservationRepository.findById(id);
    }


    public void deleteReservation(Integer id) {
        reservationRepository.deleteById(id);
    }

}
