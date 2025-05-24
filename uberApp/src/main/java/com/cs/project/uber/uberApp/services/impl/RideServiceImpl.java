package com.cs.project.uber.uberApp.services.impl;

import com.cs.project.uber.uberApp.dto.RideRequestDTO;
import com.cs.project.uber.uberApp.entities.Driver;
import com.cs.project.uber.uberApp.entities.Ride;
import com.cs.project.uber.uberApp.entities.RideRequest;
import com.cs.project.uber.uberApp.entities.Rider;
import com.cs.project.uber.uberApp.entities.enums.Status;
import com.cs.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.cs.project.uber.uberApp.repositories.RideRepository;
import com.cs.project.uber.uberApp.services.RideRequestService;
import com.cs.project.uber.uberApp.services.RideService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final RideRequestService rideRequestService;
    private final ModelMapper modelMapper;

    public RideServiceImpl(RideRepository rideRepository, RideRequestService rideRequestService, ModelMapper modelMapper) {
        this.rideRepository = rideRepository;
        this.rideRequestService = rideRequestService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Ride getRideById(Long rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found with id: " + rideId));
    }

    @Override
    public void matchWithDrivers(RideRequestDTO rideRequestDTO) {

    }

    @Override
    public Ride createnewRide(RideRequest rideRequest, Driver driver) {
        rideRequest.setStatus(Status.CONFIRMED);

        Ride ride = modelMapper.map(rideRequest, Ride.class);
        ride.setStatus(Status.CONFIRMED);
        ride.setDriver(driver);
        ride.setOtp(generateOTP());
        ride.setId(null);

        rideRequestService.update(rideRequest);
        rideRepository.save(ride);

        return ride;
    }

    @Override
    public Ride updateRideStatus(Ride ride, Status status) {
        ride.setStatus(status);
        return rideRepository.save(ride);
    }

    @Override
    public Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest) {
        return rideRepository.findByRider(rider, pageRequest);
    }

    @Override
    public Page<Ride> getAllRidesOfDriver(Driver driver, PageRequest pageRequest) {
        return rideRepository.findByDriver(driver, pageRequest);
    }

    private String generateOTP() {
        Random random = new Random();
        int otpInt = random.nextInt(1000000);
        return String.format("%06d", otpInt);
    }
}
