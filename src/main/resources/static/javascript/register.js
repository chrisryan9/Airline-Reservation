async function registerUser() {
    event.preventDefault();

  const firstname = document.getElementById("firstName").value;
  const lastname = document.getElementById("lastName").value;
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;
  const phonenumber = document.getElementById("phoneNumber").value;

  const formData = new URLSearchParams();
  formData.append("firstName", firstname);
  formData.append("lastName", lastname);
  formData.append("email", email);
  formData.append("password", password);
  formData.append("phoneNumber", phonenumber);

  // make API call to register user
  try {
    const response = await fetch("/api/users/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8",
      },
      body: formData.toString(),
    });

    if (response.ok) {
      const data = await response.json(); // Parse the response as JSON
//      console.log(data.message); // Log the success message
      window.location.href = "login.html"
    } else {
      const errorText = await response.text();
      console.error("Error registering user:", errorText);
      alert("An error occurred while registering.");
    }
  } catch (error) {
    console.error(error);
    alert("An error occurred while registering.");
  }
}

// event listener for register button
const registerBtn = document.getElementById("register-btn");
registerBtn.addEventListener("click", (event) => registerUser(event));
