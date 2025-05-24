package com.codingshuttle.project.uber.uberApp.strategies.impl;

import com.codingshuttle.project.uber.uberApp.entities.RideRequest;
import com.codingshuttle.project.uber.uberApp.services.DistanceService;
import com.codingshuttle.project.uber.uberApp.strategies.RideFareCalculationStrategy;
import org.springframework.stereotype.Service;

@Service()
public class RideFareSurgePricingCalculationStrategy implements RideFareCalculationStrategy {

    private static final Double SURGE_FACTOR = 2.0;
    private final DistanceService distanceService;

    public RideFareSurgePricingCalculationStrategy(DistanceService distanceService) {
        this.distanceService = distanceService;
    }

    @Override
    public Double calculateFare(RideRequest rideRequest) {
        Double distance = distanceService.calculateDistance(rideRequest.getPickupLocation(),
                rideRequest.getDropOffLocation());

        return distance * SURGE_FACTOR;
    }
}
