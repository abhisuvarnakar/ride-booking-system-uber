package com.cs.project.uber.uberApp.services;

import com.cs.project.uber.uberApp.entities.RideRequest;

public interface RideRequestService {
    
    RideRequest findRideRequestById(Long rideRequestId);

    void update(RideRequest rideRequest);
}
