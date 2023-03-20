package com.airlinereservation.airlinereservation;

import com.airlinereservation.airlinereservation.entities.Airport;
import com.airlinereservation.airlinereservation.entities.Flight;
import com.airlinereservation.airlinereservation.repositories.AirportRepository;
import com.airlinereservation.airlinereservation.repositories.FlightRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;

    public DatabaseLoader(FlightRepository flightRepository, AirportRepository airportRepository) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
    }

    @Override
    public void run(String... args) {
        // Create airports
        Airport newYork = new Airport("JFK", "John F. Kennedy International Airport", "New York City", "US");
        Airport losAngeles = new Airport("LAX", "Los Angeles International Airport", "Los Angeles", "US");
        Airport london = new Airport("LHR", "Heathrow Airport", "London", "UK");
        Airport paris = new Airport("CDG", "Charles de Gaulle Airport", "Paris", "FR");
        Airport tokyo = new Airport("HND", "Haneda Airport", "Tokyo", "JP");
        Airport sydney = new Airport("SYD", "Sydney Airport", "Sydney", "AU");
        Airport dubai = new Airport("DXB", "Dubai International Airport", "Dubai", "AE");
        Airport hongKong = new Airport("HKG", "Hong Kong International Airport", "Hong Kong", "HK");
        Airport frankfurt = new Airport("FRA", "Frankfurt Airport", "Frankfurt", "DE");
        Airport amsterdam = new Airport("AMS", "Amsterdam Airport Schiphol", "Amsterdam", "NL");


// Save airports to the database
        newYork = airportRepository.save(newYork);
        losAngeles = airportRepository.save(losAngeles);
        london = airportRepository.save(london);
        paris = airportRepository.save(paris);
        tokyo = airportRepository.save(tokyo);
        sydney = airportRepository.save(sydney);
        dubai = airportRepository.save(dubai);
        hongKong = airportRepository.save(hongKong);
        frankfurt = airportRepository.save(frankfurt);
        amsterdam = airportRepository.save(amsterdam);

// Create flights
        Flight flight1 = new Flight(0, newYork, losAngeles, "2023-03-20T09:00:00", "2023-03-20T12:00:00", 300);
        Flight flight2 = new Flight(0, losAngeles, newYork, "2023-03-20T15:00:00", "2023-03-20T18:00:00", 350);
        Flight flight3 = new Flight(0, newYork, london, "2023-03-20T19:00:00", "2023-03-21T07:00:00", 500);
        Flight flight4 = new Flight(0, london, newYork, "2023-03-21T08:00:00", "2023-03-21T12:00:00", 550);
        Flight flight5 = new Flight(0, london, paris, "2023-03-21T13:00:00", "2023-03-21T14:30:00", 100);
        Flight flight6 = new Flight(0, paris, london, "2023-03-21T15:00:00", "2023-03-21T16:30:00", 100);
        Flight flight7 = new Flight(0, tokyo, sydney, "2023-03-22T11:00:00", "2023-03-22T20:00:00", 600);
        Flight flight8 = new Flight(0, sydney, tokyo, "2023-03-23T07:00:00", "2023-03-23T16:00:00", 650);
        Flight flight9 = new Flight(0, newYork, hongKong, "2023-03-24T08:00:00", "2023-03-25T08:00:00", 800);
        Flight flight10 = new Flight(0, hongKong, newYork, "2023-03-25T10:00:00", "2023-03-26T06:00:00", 850);
        Flight flight11 = new Flight(0, frankfurt, amsterdam, "2023-03-26T09:00:00", "2023-03-26T10:15:00", 90);
        Flight flight12 = new Flight(0, amsterdam, frankfurt, "2023-03-26T11:30:00", "2023-03-26T12:45:00", 90);
        Flight flight13 = new Flight(0, dubai, london, "2023-03-27T13:00:00", "2023-03-27T17:30:00", 400);
        Flight flight14 = new Flight(0, london, dubai, "2023-03-27T19:00:00", "2023-03-28T05:30:00", 450);

// Save flights to the database
        flightRepository.save(flight1);
        flightRepository.save(flight2);
        flightRepository.save(flight3);
        flightRepository.save(flight4);
        flightRepository.save(flight5);
        flightRepository.save(flight6);
        flightRepository.save(flight7);
        flightRepository.save(flight8);
        flightRepository.save(flight9);
        flightRepository.save(flight10);
        flightRepository.save(flight11);
        flightRepository.save(flight12);
        flightRepository.save(flight13);
        flightRepository.save(flight14);
    }
}
