package com.codingshuttle.project.uber.uberApp.dto;

import com.codingshuttle.project.uber.uberApp.entities.enums.PaymentMethod;
import com.codingshuttle.project.uber.uberApp.entities.enums.Status;

import java.util.Date;

public class RideRequestDTO {

    private Long id;

    private PointDTO pickupLocation;

    private PointDTO dropOffLocation;

    private Date requestedTime;

    private RiderDTO rider;

    private PaymentMethod paymentMethod;

    private Status status;

    private Double fare;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PointDTO getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(PointDTO pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public PointDTO getDropOffLocation() {
        return dropOffLocation;
    }

    public void setDropOffLocation(PointDTO dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }

    public Date getRequestedTime() {
        return requestedTime;
    }

    public void setRequestedTime(Date requestedTime) {
        this.requestedTime = requestedTime;
    }

    public RiderDTO getRider() {
        return rider;
    }

    public void setRider(RiderDTO rider) {
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
