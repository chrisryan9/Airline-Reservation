package com.airlinereservation.airlinereservation.controllers;

import com.airlinereservation.airlinereservation.dtos.UserDto;
import com.airlinereservation.airlinereservation.entities.User;
import com.airlinereservation.airlinereservation.exceptions.EmailAlreadyExistsException;
import com.airlinereservation.airlinereservation.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<String> registerUser(@ModelAttribute UserDto userDto) {
        try {
            userService.registerUser(userDto);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"message\": \"User registered successfully!\"}");
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<UserDto> loginUser(@RequestParam String email, @RequestParam String password) {
        Optional<User> userOptional = userService.findUserByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (passwordEncoder.matches(password, user.getPassword())) {
                // Return user data as JSON
                UserDto userDto = new UserDto(user);
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userDto);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
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

