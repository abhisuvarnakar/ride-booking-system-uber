package com.codingshuttle.project.uber.uberApp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "t_driver", indexes = {@Index(name = "idx_t_driver1", columnList = "vehicleId")})
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Double rating;

    private Boolean available;

    private String vehicleId;

    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point currentLocation;

    public Driver() {

    }

    public Driver(User user, Double rating, Boolean available, String vehicleId, Point currentLocation) {
        this.user = user;
        this.rating = rating;
        this.available = available;
        this.vehicleId = vehicleId;
        this.currentLocation = currentLocation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Point getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Point currentLocation) {
        this.currentLocation = currentLocation;
    }
}
