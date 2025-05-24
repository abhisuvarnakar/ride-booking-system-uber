package com.codingshuttle.project.uber.uberApp.dto;

public class RatingDTO {

    private Long rideId;
    private Integer rating;

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
