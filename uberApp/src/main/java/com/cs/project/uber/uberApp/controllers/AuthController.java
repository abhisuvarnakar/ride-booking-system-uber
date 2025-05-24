package com.cs.project.uber.uberApp.controllers;

import com.cs.project.uber.uberApp.dto.*;
import com.cs.project.uber.uberApp.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/uber/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO) {
        return new ResponseEntity<>(authService.signup(signUpDTO), HttpStatus.CREATED);
    }

    @PostMapping("/onBoardNewDriver/{userId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<DriverDTO> onBoardNewDriver(@PathVariable Long userId,
                                                      @RequestBody OnboardDriverDTO onboardDriverDTO) {
        return new ResponseEntity<>(authService.onboardNewDriver(userId, onboardDriverDTO.getVehicleId()),
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response) {
        String[] tokens = authService.login(loginRequestDTO.getEmail(),
                loginRequestDTO.getPassword());

        Cookie cookie = new Cookie("token", tokens[1]);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        return ResponseEntity.ok(new LoginResponseDTO(tokens[0]));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest request) {
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found " +
                        "inside the Cookies"));

        String accessToken = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(new LoginResponseDTO(accessToken));
    }

}
