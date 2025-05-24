package com.cs.project.uber.uberApp.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "t_rating",
        indexes = {
        @Index(name = "idx_t_rating1", columnList = "rider_id"),
                @Index(name = "idx_t_rating2", columnList = "driver_id"),
})
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Ride ride;

    @ManyToOne
    private Rider rider;

    @ManyToOne
    private Driver driver;

    private Integer driverRating;

    private Integer riderRating;

    public Rating(Ride ride, Rider rider, Driver driver, Integer driverRating, Integer riderRating) {
        this.ride = ride;
        this.rider = rider;
        this.driver = driver;
        this.driverRating = driverRating;
        this.riderRating = riderRating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Integer getDriverRating() {
        return driverRating;
    }

    public void setDriverRating(Integer driverRating) {
        this.driverRating = driverRating;
    }

    public Integer getRiderRating() {
        return riderRating;
    }

    public void setRiderRating(Integer riderRating) {
        this.riderRating = riderRating;
    }
}
