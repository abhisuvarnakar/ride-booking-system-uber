package com.codingshuttle.project.uber.uberApp.services;

import com.codingshuttle.project.uber.uberApp.dto.DriverDTO;
import com.codingshuttle.project.uber.uberApp.dto.SignUpDTO;
import com.codingshuttle.project.uber.uberApp.dto.UserDTO;

public interface AuthService {

    String[] login(String email, String password);

    UserDTO signup(SignUpDTO signUpDTO);

    DriverDTO onboardNewDriver(Long userId, String vehicleId);

    String refreshToken(String refreshToken);
}
