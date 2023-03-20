package com.airlinereservation.airlinereservation.controllers;

import com.airlinereservation.airlinereservation.entities.Reservation;
import com.airlinereservation.airlinereservation.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationService.findAllReservations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Integer id) {
        return reservationService.findReservationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        return ResponseEntity.ok(reservationService.saveReservation(reservation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Integer id, @RequestBody Reservation reservation) {
        return reservationService.findReservationById(id).map(existingReservation -> {
            reservation.setReservation_id(existingReservation.getReservation_id());
            return ResponseEntity.ok(reservationService.saveReservation(reservation));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Integer id) {
        reservationService.findReservationById(id).ifPresent(reservation -> reservationService.deleteReservation(id));
        return ResponseEntity.noContent().build();
    }
}
