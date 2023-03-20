// DOM Elements
const flightContainer = document.getElementById("flight-container");
const originAirportInput = document.getElementById("originAirport");
const destinationAirportInput = document.getElementById("destinationAirport");
const searchBtn = document.getElementById("searchBtn");

const headers = {
  "Content-Type": "application/json",
};

const baseUrl = "http://localhost:8080/api/flights/";

async function getFlights() {
  let airportCities = []; // Define airportCities within the scope of the function
  let flightsData; // Add this variable to store the flights data

  await fetch(baseUrl, {
    method: "GET",
    headers: headers,
  })
    .then((response) => response.json())
    .then((data) => {
      flightsData = data; // Store the flights data in the flightsData variable
      airportCities = data.reduce((arr, flight) => {
        if (!arr.includes(flight.originAirport.airport_city)) {
          arr.push(flight.originAirport.airport_city);
        }
        if (!arr.includes(flight.destinationAirport.airport_city)) {
          arr.push(flight.destinationAirport.airport_city);
        }
        return arr;
      }, []);

      // Create the origin airport dropdown list
      const originAirportList = createAirportDropdownList(airportCities);
      originAirportInput.innerHTML = originAirportList.innerHTML;

      // Create the destination airport dropdown list
      const destinationAirportList = createAirportDropdownList(airportCities);
      destinationAirportInput.innerHTML = destinationAirportList.innerHTML;
    })
    .catch((err) => console.error(err));

  // Add event listener to search button
  searchBtn.addEventListener("click", () => {
    const selectedOriginCity = originAirportInput.value;
    const selectedDestinationCity = destinationAirportInput.value;

    // Filter flights based on selected origin and destination cities
    const filteredFlights = flightsData.filter((flight) => {
      return (
        flight.originAirport.airport_city === selectedOriginCity &&
        flight.destinationAirport.airport_city === selectedDestinationCity
      );
    });

    createFlightCards(filteredFlights);
  });
}

function createAirportDropdownList(cities) {
  const dropdown = document.createElement("select");

  const defaultOption = document.createElement("option");
  defaultOption.text = "Select an airport";
  defaultOption.value = "";
  dropdown.add(defaultOption);

  cities.forEach((city) => {
    const option = document.createElement("option");
    option.text = city;
    option.value = city;
    dropdown.add(option);
  });

  return dropdown;
}

const createFlightCards = (array) => {
  flightContainer.innerHTML = "";
  array.forEach((obj) => {
    let flightCard = document.createElement("div");
    flightCard.classList.add("m-2");
    flightCard.innerHTML = `
            <div class="card" style="width: 18rem;">
                <div class="card-body">
                    <h5 class="card-title">Flight: ${obj.flight_id}</h5>
                    <h6 class="card-subtitle mb-2 text-muted">${obj.originAirport.airport_city} (${obj.originAirport.airport_code}) to ${obj.destinationAirport.airport_city} (${obj.destinationAirport.airport_code})</h6>
                    <p class="card-text">Departure: ${obj.departure_time}</p>
                    <p class="card-text">Arrival: ${obj.arrival_time}</p>
                    <p class="card-text">Price: $${obj.price}</p>
                    <button class="btn btn-primary">Book</button>
                </div>
            </div>
        `;
    flightContainer.append(flightCard);
  });
};

getFlights();