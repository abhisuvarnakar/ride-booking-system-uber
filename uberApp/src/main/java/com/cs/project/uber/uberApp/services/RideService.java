package com.cs.project.uber.uberApp.services;

import com.cs.project.uber.uberApp.dto.RideRequestDTO;
import com.cs.project.uber.uberApp.entities.Driver;
import com.cs.project.uber.uberApp.entities.Ride;
import com.cs.project.uber.uberApp.entities.RideRequest;
import com.cs.project.uber.uberApp.entities.Rider;
import com.cs.project.uber.uberApp.entities.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RideService {

    Ride getRideById(Long rideId);

    void matchWithDrivers(RideRequestDTO rideRequestDTO);

    Ride createnewRide(RideRequest rideRequest, Driver driver);

    Ride updateRideStatus(Ride ride, Status status);

    Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest);

    Page<Ride> getAllRidesOfDriver(Driver driver, PageRequest pageRequest);
}
