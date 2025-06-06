package com.cs.project.uber.uberApp.strategies.impl;

import com.cs.project.uber.uberApp.entities.RideRequest;
import com.cs.project.uber.uberApp.services.DistanceService;
import com.cs.project.uber.uberApp.strategies.RideFareCalculationStrategy;
import org.springframework.stereotype.Service;

@Service()
public class RiderFareDefaultCalculationStrategy implements RideFareCalculationStrategy {

    private final DistanceService distanceService;

    public RiderFareDefaultCalculationStrategy(DistanceService distanceService) {
        this.distanceService = distanceService;
    }

    @Override
    public Double calculateFare(RideRequest rideRequest) {
        Double distance = distanceService.calculateDistance(rideRequest.getPickupLocation(),
                rideRequest.getDropOffLocation());

        return distance * RIDE_FARE_MULTIPLIER;
    }
}
