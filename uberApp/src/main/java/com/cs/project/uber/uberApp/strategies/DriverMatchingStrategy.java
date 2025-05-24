package com.cs.project.uber.uberApp.strategies;

import com.cs.project.uber.uberApp.entities.Driver;
import com.cs.project.uber.uberApp.entities.RideRequest;

import java.util.List;

public interface DriverMatchingStrategy {

    List<Driver> findMatchingDriver(RideRequest rideRequest);

}
