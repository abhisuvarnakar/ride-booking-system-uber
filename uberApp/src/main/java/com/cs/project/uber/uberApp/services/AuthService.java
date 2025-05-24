package com.cs.project.uber.uberApp.services;

import com.cs.project.uber.uberApp.dto.DriverDTO;
import com.cs.project.uber.uberApp.dto.SignUpDTO;
import com.cs.project.uber.uberApp.dto.UserDTO;

public interface AuthService {

    String[] login(String email, String password);

    UserDTO signup(SignUpDTO signUpDTO);

    DriverDTO onboardNewDriver(Long userId, String vehicleId);

    String refreshToken(String refreshToken);
}
