package com.airlinereservation.airlinereservation.services;

import com.airlinereservation.airlinereservation.dtos.UserDto;
import com.airlinereservation.airlinereservation.entities.User;
import com.airlinereservation.airlinereservation.exceptions.EmailAlreadyExistsException;
import com.airlinereservation.airlinereservation.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> findAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> findUserById(Integer id) {
        return userRepository.findById(id).map(UserDto::new);
    }

    @Override
    @Transactional
    public UserDto registerUser(UserDto userDto) {
        String email = userDto.getEmail();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException("Email " + email + " already exists!");
        }

        User user = new User(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.saveAndFlush(user);
        return new UserDto(savedUser);
    }

    @Override
    public List<String> loginUser(UserDto userDto) {
        List<String> response = new ArrayList<>();
        Optional<User> userOptional = userRepository.findByEmail(userDto.getEmail());
        if (userOptional.isPresent()) {
            if (passwordEncoder.matches(userDto.getPassword(), userOptional.get().getPassword())) {
                response.add("http://localhost:8080/home.html");
                response.add(String.valueOf(userOptional.get().getId()));
            } else {
                response.add("Email or password incorrect");
            }
        } else {
            response.add("Email or password incorrect");
        }
        return response;
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<Object> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }
}

