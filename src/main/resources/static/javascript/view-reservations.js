// Add this at the beginning of view-reservations.js
const currentUser = JSON.parse(localStorage.getItem("currentUser"));

// DOM Elements
const container = document.getElementById('reservations-container');

// Fetch a single reservation by ID
async function fetchReservation(reservationId) {
  try {
    const response = await fetch(`http://localhost:8080/api/reservations/${reservationId}`, {
      method: "GET",
    });

    if (!response.ok) {
      console.error("Error fetching reservation (status: " + response.status + ", " + response.statusText + ")");
      throw new Error("Error fetching reservation");
    }

    const reservation = await response.json();

    // Add the user's first name and last name to the reservation object
    if (currentUser) {
      reservation.passenger = {
        firstName: currentUser.firstName,
        lastName: currentUser.lastName,
      };
    }

    displayReservation(reservation);
  } catch (error) {
    console.error("Error fetching reservation:", error);
    alert("Failed to fetch reservation. Please try again later.");
  }
}

// Display a single reservation or a message if there are no reservations
function displayReservation(reservation) {
  container.innerHTML = '';

  if (reservation === null || reservation === undefined) {
    container.innerHTML = '<p>You currently do not have any reservations made.</p>';
  } else {
    const reservationElement = document.createElement('div');
    reservationElement.classList.add('reservation');

    reservationElement.innerHTML = `
        <p class="reservation-detail"><strong>Passenger Name:</strong> ${reservation.passenger.firstName} ${reservation.passenger.lastName}</p>
        <p class="reservation-detail"><strong>Flight Details:</strong> ${reservation.flight.originAirport.airport_city} (${reservation.flight.originAirport.airport_code}) to ${reservation.flight.destinationAirport.airport_city} (${reservation.flight.destinationAirport.airport_code})</p>
        <p class="reservation-detail"><strong>Price:</strong> ${reservation.price}</p>
        <button class="delete-btn">Delete</button>
    `;

    container.appendChild(reservationElement);

    // Add event listener for the delete button
    const deleteBtn = reservationElement.querySelector(".delete-btn");
    deleteBtn.addEventListener("click", () => {
      deleteReservation(reservation.reservation_id);
    });
  }
}

// Function to delete a reservation
async function deleteReservation(reservationId) {
  try {
    const response = await fetch(`http://localhost:8080/api/reservations/${reservationId}`, {
      method: "DELETE",
    });

    if (!response.ok) {
      console.error("Error deleting reservation (status: " + response.status + ", " + response.statusText + ")");
      throw new Error("Error deleting reservation");
    }

    alert("Reservation successfully deleted!");
    container.innerHTML = '<p>You currently do not have any reservations made.</p>';
  } catch (error) {
    console.error("Error deleting reservation:", error);
    alert("Failed to delete reservation. Please try again later.");
  }
}

// Fetch and display a single reservation by ID when the page is loaded
// Replace '1' with the actual reservation ID you want to fetch
fetchReservation(1);