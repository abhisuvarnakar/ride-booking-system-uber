package com.cs.project.uber.uberApp.services.impl;

import com.cs.project.uber.uberApp.dto.DriverDTO;
import com.cs.project.uber.uberApp.dto.SignUpDTO;
import com.cs.project.uber.uberApp.dto.UserDTO;
import com.cs.project.uber.uberApp.entities.Driver;
import com.cs.project.uber.uberApp.entities.User;
import com.cs.project.uber.uberApp.entities.enums.Role;
import com.cs.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.cs.project.uber.uberApp.exceptions.RuntimeConflictException;
import com.cs.project.uber.uberApp.repositories.UserRepository;
import com.cs.project.uber.uberApp.security.JwtService;
import com.cs.project.uber.uberApp.services.AuthService;
import com.cs.project.uber.uberApp.services.DriverService;
import com.cs.project.uber.uberApp.services.RiderService;
import com.cs.project.uber.uberApp.services.WalletService;
import com.cs.project.uber.uberApp.util.Utils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RiderService riderService;
    private final WalletService walletService;
    private final DriverService driverService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthServiceImpl(ModelMapper mapper, UserRepository userRepository, RiderService riderService, WalletService walletService, DriverService driverService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.modelMapper = mapper;
        this.userRepository = userRepository;
        this.riderService = riderService;
        this.walletService = walletService;
        this.driverService = driverService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public String[] login(String email, String password) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,
                password));

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new String[] {accessToken, refreshToken};
    }

    @Override
    @Transactional
    public UserDTO signup(SignUpDTO signUpDTO) {
        Optional<User> user = userRepository.findByEmail(signUpDTO.getEmail());
        if (user.isPresent()) {
            throw new RuntimeConflictException("Cannot signup, User already exists with email = " + signUpDTO.getEmail());
        }

        User mappedUser = modelMapper.map(signUpDTO, User.class);
        mappedUser.setRoles(Set.of(Role.RIDER));
        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
        User savedUser = userRepository.save(mappedUser);

        // Create user related entities
        riderService.createNewRider(savedUser);

        //TODO - Add wallet related service.
        walletService.createNewWallet(savedUser);

        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public DriverDTO onboardNewDriver(Long userId, String vehicleId) {
        User user =
                userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + userId));

        if (user.getRoles().contains(Role.DRIVER)) {
            Utils.throwRuntimeConflictException("User with id: " + userId + " is already a Driver" +
                    ".");
        }

        Driver createDriver = new Driver(user, 0.0, true, vehicleId, null);
        user.getRoles().add(Role.DRIVER);
        userRepository.save(user);
        Driver savedDriver = driverService.createNewDriver(createDriver);

        return modelMapper.map(savedDriver, DriverDTO.class);
    }

    @Override
    public String refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        User user =
                userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + userId));

        return jwtService.generateAccessToken(user);
    }

}
