package com.cs.project.uber.uberApp.services;

import com.cs.project.uber.uberApp.dto.DriverDTO;
import com.cs.project.uber.uberApp.dto.RiderDTO;
import com.cs.project.uber.uberApp.entities.Ride;

public interface RatingService {

    DriverDTO rateDriver(Ride ride, Integer rating);

    RiderDTO rateRider(Ride ride, Integer rating);

    void createNewRating(Ride ride);

}
