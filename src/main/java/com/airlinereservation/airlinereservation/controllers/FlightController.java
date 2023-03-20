package com.airlinereservation.airlinereservation.controllers;

import com.airlinereservation.airlinereservation.entities.Airport;
import com.airlinereservation.airlinereservation.entities.Flight;
import com.airlinereservation.airlinereservation.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights/")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights() {
        return ResponseEntity.ok(flightService.findAllFlights());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Integer id) {
        return flightService.findFlightById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        return ResponseEntity.ok(flightService.saveFlight(flight));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Integer id, @RequestBody Flight flight) {
        return flightService.findFlightById(id).map(existingFlight -> {
            flight.setFlight_id(existingFlight.getFlight_id());
            return ResponseEntity.ok(flightService.saveFlight(flight));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Integer id) {
        flightService.findFlightById(id).ifPresent(flight -> flightService.deleteFlight(id));
        return ResponseEntity.noContent().build();
    }
}

