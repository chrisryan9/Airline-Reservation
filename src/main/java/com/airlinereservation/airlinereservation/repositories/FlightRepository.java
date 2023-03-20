package com.airlinereservation.airlinereservation.repositories;

import com.airlinereservation.airlinereservation.entities.Airport;
import com.airlinereservation.airlinereservation.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {
}
