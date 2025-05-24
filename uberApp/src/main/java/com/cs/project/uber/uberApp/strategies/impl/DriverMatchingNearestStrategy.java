package com.cs.project.uber.uberApp.strategies.impl;

import com.cs.project.uber.uberApp.entities.Driver;
import com.cs.project.uber.uberApp.entities.RideRequest;
import com.cs.project.uber.uberApp.repositories.DriverRepository;
import com.cs.project.uber.uberApp.strategies.DriverMatchingStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service()
public class DriverMatchingNearestStrategy implements DriverMatchingStrategy {

    private final DriverRepository driverRepository;

    public DriverMatchingNearestStrategy(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public List<Driver> findMatchingDriver(RideRequest rideRequest) {
        return driverRepository.findTenNearestDrivers(rideRequest.getPickupLocation());
    }

}
