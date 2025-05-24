package com.codingshuttle.project.uber.uberApp.services.impl;

import com.codingshuttle.project.uber.uberApp.dto.DriverDTO;
import com.codingshuttle.project.uber.uberApp.dto.RideDTO;
import com.codingshuttle.project.uber.uberApp.dto.RideRequestDTO;
import com.codingshuttle.project.uber.uberApp.dto.RiderDTO;
import com.codingshuttle.project.uber.uberApp.entities.*;
import com.codingshuttle.project.uber.uberApp.entities.enums.Status;
import com.codingshuttle.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.project.uber.uberApp.repositories.RideRequestRepository;
import com.codingshuttle.project.uber.uberApp.repositories.RiderRepository;
import com.codingshuttle.project.uber.uberApp.services.DriverService;
import com.codingshuttle.project.uber.uberApp.services.RatingService;
import com.codingshuttle.project.uber.uberApp.services.RideService;
import com.codingshuttle.project.uber.uberApp.services.RiderService;
import com.codingshuttle.project.uber.uberApp.strategies.RideStrategyManager;
import com.codingshuttle.project.uber.uberApp.util.Utils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RiderServiceImpl implements RiderService {

    private static final Logger log = LoggerFactory.getLogger(RiderServiceImpl.class);
    private final ModelMapper modelMapper;
    private final RideRequestRepository rideRequestRepository;
    private final RideStrategyManager rideStrategyManager;
    private final RiderRepository riderRepository;
    private final RideService rideService;
    private final DriverService driverService;
    private final RatingService ratingService;

    public RiderServiceImpl(ModelMapper modelMapper,
                            RideRequestRepository rideRequestRepository, RideStrategyManager rideStrategyManager, RiderRepository riderRepository, RideService rideService, DriverService driverService, RatingService ratingService) {
        this.modelMapper = modelMapper;
        this.rideRequestRepository = rideRequestRepository;
        this.rideStrategyManager = rideStrategyManager;
        this.riderRepository = riderRepository;
        this.rideService = rideService;
        this.driverService = driverService;
        this.ratingService = ratingService;
    }

    @Override
    @Transactional
    public RideRequestDTO requestRide(RideRequestDTO rideRequestDTO) {
        Rider rider = getCurrentRider();
        RideRequest rideRequest = modelMapper.map(rideRequestDTO, RideRequest.class);
        rideRequest.setStatus(Status.PENDING);
        rideRequest.setRider(rider);

        Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);

        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);
        List<Driver> drivers =
                rideStrategyManager.driverMatchingStrategy(getCurrentRider().getRating()).findMatchingDriver(rideRequest);

        //TODO - Send notification to all the drivers about this ride request
        return modelMapper.map(savedRideRequest, RideRequestDTO.class);
    }

    @Override
    public RideDTO cancelRide(Long rideId) {
        Rider rider = getCurrentRider();
        Ride ride = rideService.getRideById(rideId);

        if (!rider.equals(ride.getRider())) {
            Utils.throwRuntimeException("Rider does not own this ride with id: " + rideId);
        }

        if (!ride.getStatus().equals(Status.CONFIRMED)) {
            Utils.throwRuntimeException("Ride cannot be cancelled, invalid status: " + ride.getStatus());
        }

        Ride savedRide = rideService.updateRideStatus(ride, Status.CANCELLED);
        driverService.updateDriverAvailability(ride.getDriver(), true);

        return modelMapper.map(savedRide, RideDTO.class);
    }

    @Override
    public DriverDTO rateDriver(Long rideId, Integer rating) {
        Ride ride = rideService.getRideById(rideId);
        Rider rider = getCurrentRider();

        if (!rider.equals(ride.getRider())) {
            Utils.throwRuntimeException("Rider is not the owned of this ride.");
        }

        if (!ride.getStatus().equals(Status.ENDED)) {
            Utils.throwRuntimeException("Ride status is not ENDED. Hence cannot start rating, " +
                    "status: " + ride.getStatus());
        }

        return ratingService.rateDriver(ride, rating);
    }

    @Override
    public RiderDTO getMyProfile() {
        Rider currenttRider = getCurrentRider();
        return modelMapper.map(currenttRider, RiderDTO.class);
    }

    @Override
    public Page<RideDTO> getAllMyRides(PageRequest pageRequest) {
        Rider currentRider = getCurrentRider();
        return rideService.getAllRidesOfRider(currentRider, pageRequest).map(
                ride -> modelMapper.map(ride, RideDTO.class)
        );
    }

    @Override
    public Rider createNewRider(User user) {
        Rider rider = new Rider(user, 0.0);
        return riderRepository.save(rider);
    }

    @Override
    public Rider getCurrentRider() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //TODO - implement spring security
        return riderRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(
                "Rider not associated with use with id : " + user.getId()
        ));
    }
}
