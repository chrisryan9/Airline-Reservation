
// function to log in user
function loginUser() {
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;

  const user = {
    email,
    password,
  };

  // make API call to log in user
  fetch("/api/users/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(user),
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      alert("Login successful!");
    })
    .catch((error) => {
      console.error(error);
      alert("An error occurred while logging in.");
    });
}

// event listener for login button
const loginBtn = document.getElementById("login-btn");
loginBtn.addEventListener("click", loginUser);
