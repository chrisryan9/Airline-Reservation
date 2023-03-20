// function to register user
function registerUser() {
  const firstname = document.getElementById("firstname").value;
  const lastname = document.getElementById("lastname").value;
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;
  const phonenumber = document.getElementById("phonenumber").value;

  const user = {
    firstname,
    lastname,
    email,
    password,
    phonenumber,
  };

  // make API call to register user
  fetch("/api/users/register", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(user),
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      alert("User registered successfully!");
    })
    .catch((error) => {
      console.error(error);
      alert("An error occurred while registering the user.");
    });
}

// event listener for register button
const registerBtn = document.getElementById("register-btn");
registerBtn.addEventListener("click", registerUser);
