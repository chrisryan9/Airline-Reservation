// DOM Elements
const searchForm = document.querySelector("form"); // Add this line to get the form element

// Add the event listener for the form
searchForm.addEventListener("submit", (event) => {
    event.preventDefault(); // Prevent the form from submitting and causing a page reload
});

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

             async function bookFlight(flightId, price, origin, destination) {
               const reservationUrl = "http://localhost:8080/api/reservations";

               // Check if the user is logged in

               console.log("LocalStorage state before getting currentUser:", localStorage);

               const currentUser = JSON.parse(localStorage.getItem("currentUser"));

             //        console.log('Current user data:', currentUser);
             //        console.log('Current user ID:', currentUser.userId); // Use 'userId' instead of 'user_id'

               if (!currentUser || !currentUser.userId) {
                 alert("Please log in to book a flight.");
                 return;
               }

               const userId = currentUser.id;

               const reservationData = {
                 user: {
                   userId: userId, // Use 'userId' instead of 'user_id'
                 },
                 flight: {
                   flight_id: flightId,
                 },
                 reservation_status: "Reserved",
                 payment_status: "Pending",
                 price: price,
               };

               try {
                 const response = await fetch(reservationUrl, {
                   method: "POST",
                   headers: headers,
                   body: JSON.stringify(reservationData),
                 });

                 if (response.ok) {
                     const data = await response.json();
                     console.log("Reservation created:", data);
                     localStorage.setItem(
                       "selectedFlight",
                       JSON.stringify({ flightId, price, origin, destination })
                     );
                     window.location.href = 'confirm_booking.html';
                   } else {
                   console.error("Error creating reservation:", response.status);
                   alert("Error reserving flight.");
                 }
               } catch (err) {
                 console.error("Error booking flight:", err);
                 alert("Error reserving flight.");
               }
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
                                 <button class="btn btn-primary" data-flight-id="${obj.flight_id}">Book</button>
                             </div>
                         </div>
                     `;
                 flightContainer.append(flightCard);

                 // Add the event listener for the "Book" button
                 const bookButton = flightCard.querySelector("button");
                 bookButton.addEventListener("click", () => {
                   bookFlight(
                     obj.flight_id,
                     obj.price,
                     `${obj.originAirport.airport_city} (${obj.originAirport.airport_code})`,
                     `${obj.destinationAirport.airport_city} (${obj.destinationAirport.airport_code})`
                   );
                 });
               });
             };

             getFlights();