package com.airlinereservation.airlinereservation.controllers;

import com.airlinereservation.airlinereservation.dtos.UserDto;
import com.airlinereservation.airlinereservation.entities.User;
import com.airlinereservation.airlinereservation.exceptions.EmailAlreadyExistsException;
import com.airlinereservation.airlinereservation.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.findAllUsers();
        List<UserDto> userDTOs = users.stream().map(UserDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) {
        return userService.findUserById(id)
                .map(user -> ResponseEntity.ok(new UserDto(user.toEntity())))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> registerUser(@ModelAttribute UserDto userDto, HttpServletResponse response) throws IOException {
        try {
            userService.registerUser(userDto);
            response.sendRedirect("/login.html"); // Redirect to login page
            return null; // Return null since the response has already been sent
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String email, @RequestParam String password, HttpServletResponse response) throws IOException {
        // The actual authentication logic should be handled by a security filter (e.g., Spring Security)
        // In this case, we assume that the login is successful
        response.sendRedirect("/user-home.html"); // Redirect to user home page
        return null; // Return null since the response has already been sent
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer id, @RequestBody UserDto userDto) {
        return userService.findUserById(id).map(existingUser -> {
            userDto.setUserId(existingUser.getUserId()); // Set the existing user's ID to the userDto

            // Only update the password if it's different from the existing one
            if (!existingUser.getPassword().equals(userDto.getPassword())) {
                userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }

            UserDto savedUserDto = userService.registerUser(userDto);
            return ResponseEntity.ok(savedUserDto);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.findUserById(id).ifPresent(user -> userService.deleteUser(id));
        return ResponseEntity.noContent().build();
    }
}

