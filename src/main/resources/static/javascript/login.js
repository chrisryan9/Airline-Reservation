function loginUser() {
  console.log("loginUser() function called");
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;

  console.log("Trying to log in user:", { email, password });

  fetch("http://localhost:8080/api/users/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    body: new URLSearchParams({ email, password }),
  })
    .then((response) => {
      console.log("Login response:", response);
      if (response.ok) {
        response.json().then((userData) => {
          console.log("User data:", userData);
          localStorage.setItem("currentUser", JSON.stringify(userData));
          console.log(
            "User data saved to localStorage:",
            JSON.parse(localStorage.getItem("userData"))
          );
          window.location.href = "/user-home.html";
        });
      } else if (response.status === 401) {
        alert("Email or password incorrect");
      } else if (response.status === 404) {
        alert("User not found");
      } else {
        alert("An error occurred while logging in");
      }
    })
    .catch((error) => {
      console.log("Error logging in:", error);
      alert("An error occurred while logging in");
    });
}