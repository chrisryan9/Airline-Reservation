package com.airlinereservation.airlinereservation.services;

import com.airlinereservation.airlinereservation.dtos.UserDto;
import com.airlinereservation.airlinereservation.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> findAllUsers();
    Optional<UserDto> findUserById(Integer id);
    UserDto registerUser(UserDto userDto);
    List<String> loginUser(UserDto userDto);
    void deleteUser(Integer id); // Add deleteUser method signature

    Optional<User> findUserByEmail(String email);
}