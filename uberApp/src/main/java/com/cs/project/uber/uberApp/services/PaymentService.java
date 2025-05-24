package com.cs.project.uber.uberApp.services;

import com.cs.project.uber.uberApp.entities.Payment;
import com.cs.project.uber.uberApp.entities.Ride;
import com.cs.project.uber.uberApp.entities.enums.Status;

public interface PaymentService {

    void processPayment(Ride ride);

    Payment createNewPayment(Ride ride);

    void updatePaymentStatus(Payment payment, Status status);
}
