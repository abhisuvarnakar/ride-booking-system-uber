package com.cs.project.uber.uberApp.strategies;

import com.cs.project.uber.uberApp.entities.Payment;

public interface PaymentStrategy {

    static final Double PLATFORM_COMMISION = 0.3;

    void processPayment(Payment payment);
}
