package com.cs.project.uber.uberApp.services.impl;

import com.cs.project.uber.uberApp.dto.DriverDTO;
import com.cs.project.uber.uberApp.dto.RideDTO;
import com.cs.project.uber.uberApp.dto.RiderDTO;
import com.cs.project.uber.uberApp.entities.Driver;
import com.cs.project.uber.uberApp.entities.Ride;
import com.cs.project.uber.uberApp.entities.RideRequest;
import com.cs.project.uber.uberApp.entities.User;
import com.cs.project.uber.uberApp.entities.enums.Status;
import com.cs.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.cs.project.uber.uberApp.repositories.DriverRepository;
import com.cs.project.uber.uberApp.services.*;
import com.cs.project.uber.uberApp.util.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class DriverServiceImpl implements DriverService {

    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final PaymentService paymentService;
    private final ModelMapper modelMapper;
    private final RatingService ratingService;

    public DriverServiceImpl(RideRequestService rideRequestService, DriverRepository driverRepository, RideService rideService, PaymentService paymentService, ModelMapper modelMapper, RatingService ratingService) {
        this.rideRequestService = rideRequestService;
        this.driverRepository = driverRepository;
        this.rideService = rideService;
        this.paymentService = paymentService;
        this.modelMapper = modelMapper;
        this.ratingService = ratingService;
    }

    @Override
    @Transactional
    public RideDTO acceptRide(Long rideId) {
        RideRequest rideRequest = rideRequestService.findRideRequestById(rideId);

        if (!rideRequest.getStatus().equals(Status.PENDING)) {
            Utils.throwRuntimeException("Ride request cannot be accepted, status is: " + rideRequest.getStatus());
        }

        Driver currentDriver = getCurrentDriver();
        if (!currentDriver.getAvailable()) {
            Utils.throwRuntimeException("Driver cannot accept ride due to unavailability.");
        }

        Driver savedDriver = updateDriverAvailability(currentDriver, false);
        Ride ride = rideService.createnewRide(rideRequest, savedDriver);

        return modelMapper.map(ride, RideDTO.class);
    }

    @Override
    public RideDTO cancelRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if (!driver.equals(ride.getDriver())) {
            Utils.throwRuntimeException("Driver cannot start a ride as he has not accepted it " +
                    "earlier.");
        }

        if (!ride.getStatus().equals(Status.CONFIRMED)) {
            Utils.throwRuntimeException("Ride cannot be cancelled, invalid status: " + ride.getStatus());
        }

        Ride savedRide = rideService.updateRideStatus(ride, Status.CANCELLED);
        updateDriverAvailability(driver, true);

        return modelMapper.map(savedRide, RideDTO.class);
    }

    @Override
    public RideDTO startRide(Long rideId, String otp) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if (!driver.equals(ride.getDriver())) {
            Utils.throwRuntimeException("Driver cannot start a ride as he has not accepted it " +
                    "earlier.");
        }

        if (!ride.getStatus().equals(Status.CONFIRMED)) {
            Utils.throwRuntimeException("Ride status is not CONFIRMED. Hence cannot be started, " +
                    "status: " + ride.getStatus());
        }

        if (!otp.equals(ride.getOtp())) {
            Utils.throwRuntimeException("OTP is not valid. otp: " + otp);
        }

        ride.setStartedAt(new Date());
        Ride savedride = rideService.updateRideStatus(ride, Status.ONGOING);

        paymentService.createNewPayment(savedride);
        ratingService.createNewRating(savedride);

        return modelMapper.map(savedride, RideDTO.class);
    }

    @Override
    public RideDTO endRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if (!driver.equals(ride.getDriver())) {
            Utils.throwRuntimeException("Driver cannot end a ride as he has not accepted it " +
                    "earlier.");
        }

        if (!ride.getStatus().equals(Status.ONGOING)) {
            Utils.throwRuntimeException("Ride status is not ONGOING. Hence cannot be ended, " +
                    "status: " + ride.getStatus());
        }

        ride.setEndedAt(new Date());
        Ride savedRide = rideService.updateRideStatus(ride, Status.ENDED);
        updateDriverAvailability(driver, true);

        paymentService.processPayment(ride);

        return modelMapper.map(savedRide, RideDTO.class);
    }

    @Override
    public RiderDTO rateRider(Long rideId, Integer rating) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if (!driver.equals(ride.getDriver())) {
            Utils.throwRuntimeException("Driver is not the owned of this ride.");
        }

        if (!ride.getStatus().equals(Status.ENDED)) {
            Utils.throwRuntimeException("Ride status is not ENDED. Hence cannot start rating, " +
                    "status: " + ride.getStatus());
        }

        return ratingService.rateRider(ride, rating);
    }

    @Override
    public DriverDTO getMyProfile() {
        return modelMapper.map(getCurrentDriver(), DriverDTO.class);
    }

    @Override
    public Page<RideDTO> getAllMyRides(PageRequest pageRequest) {
        Driver currentDriver = getCurrentDriver();
        return rideService.getAllRidesOfDriver(currentDriver, pageRequest).map(
                ride -> modelMapper.map(ride, RideDTO.class)
        );
    }

    @Override
    public Driver getCurrentDriver() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return driverRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(
                "Driver not associated with user  with id: " + user.getId()));
    }

    @Override
    public Driver updateDriverAvailability(Driver driver, boolean available) {
        driver.setAvailable(available);
        return driverRepository.save(driver);
    }

    @Override
    public Driver createNewDriver(Driver driver) {
        return driverRepository.save(driver);
    }
}
