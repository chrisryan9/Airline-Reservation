package com.airlinereservation.airlinereservation.dtos;

import com.airlinereservation.airlinereservation.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;

    public UserDto(UserDto userDto) {
        this.userId = userDto.getUserId();
        this.firstName = userDto.getFirstName();
        this.lastName = userDto.getLastName();
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
        this.phoneNumber = userDto.getPhoneNumber();
    }

    public UserDto(User user) {
    }

    public User toEntity() {
        User user = new User(this);
        user.setId(this.userId);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setPhoneNumber(this.phoneNumber);
        return user;
    }
}

