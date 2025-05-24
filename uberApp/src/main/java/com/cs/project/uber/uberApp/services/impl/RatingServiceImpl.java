package com.cs.project.uber.uberApp.services.impl;

import com.cs.project.uber.uberApp.dto.DriverDTO;
import com.cs.project.uber.uberApp.dto.RiderDTO;
import com.cs.project.uber.uberApp.entities.Driver;
import com.cs.project.uber.uberApp.entities.Rating;
import com.cs.project.uber.uberApp.entities.Ride;
import com.cs.project.uber.uberApp.entities.Rider;
import com.cs.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.cs.project.uber.uberApp.repositories.DriverRepository;
import com.cs.project.uber.uberApp.repositories.RatingRepository;
import com.cs.project.uber.uberApp.repositories.RiderRepository;
import com.cs.project.uber.uberApp.services.RatingService;
import com.cs.project.uber.uberApp.util.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;
    private final ModelMapper modelMapper;

    public RatingServiceImpl(RatingRepository ratingRepository, DriverRepository driverRepository, RiderRepository riderRepository, ModelMapper modelMapper) {
        this.ratingRepository = ratingRepository;
        this.driverRepository = driverRepository;
        this.riderRepository = riderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DriverDTO rateDriver(Ride ride, Integer rating) {
        Rating ratingObj =
                ratingRepository.findByRide(ride).orElseThrow(() -> new ResourceNotFoundException("Rating not found for ride with Id: " + ride.getId()));

        if (ratingObj.getDriverRating() != null) {
            Utils.throwRuntimeConflictException("Driver has already been rated, cannot rate again");
        }

        ratingObj.setDriverRating(rating);
        ratingRepository.save(ratingObj);

        Driver driver = ride.getDriver();

        Double newRating = ratingRepository.findByDriver(driver)
                .stream().mapToDouble(Rating::getDriverRating)
                .average().orElse(0.0);

        driver.setRating(newRating);
        Driver savedDriver = driverRepository.save(driver);

        return modelMapper.map(savedDriver, DriverDTO.class);
    }

    @Override
    public RiderDTO rateRider(Ride ride, Integer rating) {
        Rating ratingObj =
                ratingRepository.findByRide(ride).orElseThrow(() -> new ResourceNotFoundException("Rating not found for ride with Id: " + ride.getId()));
        ratingObj.setRiderRating(rating);

        if (ratingObj.getRiderRating() != null) {
            Utils.throwRuntimeConflictException("Rider has already been rated, cannot rate again");
        }

        ratingRepository.save(ratingObj);

        Rider rider = ride.getRider();

        Double newRating = ratingRepository.findByRider(rider)
                .stream().mapToDouble(Rating::getRiderRating)
                .average().orElse(0.0);

        rider.setRating(newRating);
        Rider savedRider = riderRepository.save(rider);

        return modelMapper.map(savedRider, RiderDTO.class);
    }

    @Override
    public void createNewRating(Ride ride) {
        Rating rating = new Rating(ride, ride.getRider(), ride.getDriver(), null, null);
        ratingRepository.save(rating);
    }
}
