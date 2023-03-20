package com.airlinereservation.airlinereservation.services;

import com.airlinereservation.airlinereservation.entities.Reservation;
import com.airlinereservation.airlinereservation.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> findReservationById(Integer id) {
        return reservationRepository.findById(id);
    }

    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Integer id) {
        reservationRepository.deleteById(id);
    }
}
