package com.codingshuttle.project.uber.uberApp.strategies;

import com.codingshuttle.project.uber.uberApp.entities.RideRequest;

public interface RideFareCalculationStrategy {

    Double RIDE_FARE_MULTIPLIER = 10.0;

    Double calculateFare(RideRequest rideRequest);
}
