package com.codingshuttle.project.uber.uberApp.dto;

import com.codingshuttle.project.uber.uberApp.entities.enums.PaymentMethod;
import com.codingshuttle.project.uber.uberApp.entities.enums.Status;
import org.locationtech.jts.geom.Point;

import java.util.Date;

public class RideDTO {

    private Long id;

    private PointDTO pickupLocation;

    private PointDTO dropOffLocation;

    private Date createdTime;

    private RiderDTO rider;

    private DriverDTO driver;

    private PaymentMethod paymentMethod;

    private Status status;

    private Double fare;

    private Date startedAt;

    private Date endedAt;

    private String otp;

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

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public RiderDTO getRider() {
        return rider;
    }

    public void setRider(RiderDTO rider) {
        this.rider = rider;
    }

    public DriverDTO getDriver() {
        return driver;
    }

    public void setDriver(DriverDTO driver) {
        this.driver = driver;
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

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Date getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(Date endedAt) {
        this.endedAt = endedAt;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
