package com.airlinereservation.airlinereservation.repositories;

import com.airlinereservation.airlinereservation.entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Integer> {
}
