package com.airlinereservation.airlinereservation.services;

import com.airlinereservation.airlinereservation.entities.Airport;
import com.airlinereservation.airlinereservation.repositories.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirportService {

    @Autowired
    private AirportRepository airportRepository;

    public List<Airport> findAllAirports() {
        return airportRepository.findAll();
    }

    public Optional<Airport> findAirportById(Integer id) {
        return airportRepository.findById(id);
    }

    public Airport saveAirport(Airport airport) {
        return airportRepository.save(airport);
    }

    public void deleteAirport(Integer id) {
        airportRepository.deleteById(id);
    }
}
