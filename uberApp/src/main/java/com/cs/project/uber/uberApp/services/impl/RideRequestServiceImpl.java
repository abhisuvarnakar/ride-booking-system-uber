package com.cs.project.uber.uberApp.services.impl;

import com.cs.project.uber.uberApp.entities.RideRequest;
import com.cs.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.cs.project.uber.uberApp.repositories.RideRequestRepository;
import com.cs.project.uber.uberApp.services.RideRequestService;
import org.springframework.stereotype.Service;

@Service
public class RideRequestServiceImpl implements RideRequestService {

    private final RideRequestRepository rideRequestRepository;

    public RideRequestServiceImpl(RideRequestRepository rideRequestRepository) {
        this.rideRequestRepository = rideRequestRepository;
    }

    @Override
    public RideRequest findRideRequestById(Long rideRequestId) {
        return rideRequestRepository.findById(rideRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("RideRequest not found with ID: " + rideRequestId));
    }

    @Override
    public void update(RideRequest rideRequest) {
        RideRequest toSave = rideRequestRepository.findById(rideRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("RideRequest not found with id: " + rideRequest.getId()));

        rideRequestRepository.save(toSave);
    }
}
