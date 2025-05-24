package com.codingshuttle.project.uber.uberApp.entities;

import com.codingshuttle.project.uber.uberApp.entities.enums.PaymentMethod;
import com.codingshuttle.project.uber.uberApp.entities.enums.Status;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.util.Date;

@Entity
@Table(name = "t_ride_request",
        indexes = {@Index(name = "idx_t_ride_request1", columnList = "rider_id")})
public class RideRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pickup_location", columnDefinition = "geometry(Point, 4326)")
    //@Convert(converter = PointConverter.class)
    private Point pickupLocation;

    @Column(name = "drop_off_location", columnDefinition = "geometry(Point, 4326)")
    //@Convert(converter = PointConverter.class)
    private Point dropOffLocation;

    @CreationTimestamp
    private Date requestedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Rider rider;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Double fare;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Point getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(Point pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public Point getDropOffLocation() {
        return dropOffLocation;
    }

    public void setDropOffLocation(Point dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }

    public Date getRequestedTime() {
        return requestedTime;
    }

    public void setRequestedTime(Date requestedTime) {
        this.requestedTime = requestedTime;
    }

    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }
}
