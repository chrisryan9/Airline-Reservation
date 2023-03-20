package com.airlinereservation.airlinereservation.controllers;

import com.airlinereservation.airlinereservation.entities.Airport;
import com.airlinereservation.airlinereservation.services.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

    @Autowired
    private AirportService airportService;

    @GetMapping
    public ResponseEntity<List<Airport>> getAllAirports() {
        return ResponseEntity.ok(airportService.findAllAirports());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Airport> getAirportById(@PathVariable Integer id) {
        return airportService.findAirportById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Airport> createAirport(@RequestBody Airport airport) {
        return ResponseEntity.ok(airportService.saveAirport(airport));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Airport> updateAirport(@PathVariable Integer id, @RequestBody Airport airport) {
        return airportService.findAirportById(id).map(existingAirport -> {
            airport.setAirport_id(existingAirport.getAirport_id());
            return ResponseEntity.ok(airportService.saveAirport(airport));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirport(@PathVariable Integer id) {
        airportService.findAirportById(id).ifPresent(airport -> airportService.deleteAirport(id));
        return ResponseEntity.noContent().build();
    }
}
