  const currentUser = JSON.parse(localStorage.getItem("currentUser"));

// Add the getUrlParameter function
function getUrlParameter(name) {
  name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
  var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
  var results = regex.exec(location.search);
  return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}


document.getElementById('confirmBooking').addEventListener('click', async function() {
  // Check if the user is logged in

  if (!currentUser || !currentUser.userId) {
    alert('Please log in to confirm your booking.');
    return;
  }
//
  // Save the booking details to the database here
  const flightId = getUrlParameter('flight');
  const price = getUrlParameter('price');
  const userId = currentUser.id;

  const reservationUrl = "http://localhost:8080/api/reservations";

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
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(reservationData),
    });

    if (response.ok) {
      const data = await response.json();
      console.log("Reservation created:", data);
      // Navigate to the booking confirmation page after saving to the database
      window.location.href = 'booking_success.html';
    } else {
      console.error("Error creating reservation:", response.status);
      alert("Error reserving flight.");
    }
  } catch (err) {
    console.error("Error booking flight:", err);
    alert("Error reserving flight.");
  }
});

function displayBookingDetails() {
  const selectedFlight = JSON.parse(localStorage.getItem("selectedFlight"));

  if (currentUser && selectedFlight) {
    document.getElementById("name").innerText = `${currentUser.firstName} ${currentUser.lastName}`;
    document.getElementById("flight").innerText = `${selectedFlight.origin} to ${selectedFlight.destination}`;
    document.getElementById("price").innerText = `$${selectedFlight.price}`;
  } else {
    alert("Error retrieving booking details.");
  }
}

displayBookingDetails();
