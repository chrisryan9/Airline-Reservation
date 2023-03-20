package com.airlinereservation.airlinereservation.services;

import com.airlinereservation.airlinereservation.entities.Airport;
import com.airlinereservation.airlinereservation.entities.Flight;
import com.airlinereservation.airlinereservation.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    @Value("${aviationstack.apiKey}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FlightRepository flightRepository;

    public List<Flight> findAllFlights() {
        return flightRepository.findAll();
    }

    public Optional<Flight> findFlightById(Integer id) {
        return flightRepository.findById(id);
    }

    public Flight saveFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    public void deleteFlight(Integer id) {
        flightRepository.deleteById(id);
    }

    public <FlightData> List<Flight> getFlightsFromApi(String airportCode, LocalDate flightDate) {
        String url = "http://api.aviationstack.com/v1/flights?" +
                "access_key=" + apiKey +
                "&flight_date=" + flightDate +
                "&dep_iata=" + airportCode;

        FlightResponse response = restTemplate.getForObject(url, FlightResponse.class);

        List<Flight> flights = new ArrayList<>();
        for (Flight flightData : response.getData()) {
            Flight flight = new Flight();
            flight.setOriginAirport(new Airport(flightData.getDepartureAirport()));
            flight.setDestinationAirport(new Airport(flightData.getArrivalAirport()));
            flight.setDeparture_time(flightData.getDepartureTime());
            flight.setArrival_time(flightData.getArrivalTime());
            flight.setPrice(flightData.getPrice());
            flights.add(flight);
        }

        return flights;
    }
}
