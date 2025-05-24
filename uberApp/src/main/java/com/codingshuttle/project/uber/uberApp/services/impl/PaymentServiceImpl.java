package com.codingshuttle.project.uber.uberApp.services.impl;

import com.codingshuttle.project.uber.uberApp.entities.Payment;
import com.codingshuttle.project.uber.uberApp.entities.Ride;
import com.codingshuttle.project.uber.uberApp.entities.enums.Status;
import com.codingshuttle.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.project.uber.uberApp.repositories.PaymentRepository;
import com.codingshuttle.project.uber.uberApp.services.PaymentService;
import com.codingshuttle.project.uber.uberApp.strategies.PaymentStrategyManager;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentStrategyManager paymentStrategyManager;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentStrategyManager paymentStrategyManager) {
        this.paymentRepository = paymentRepository;
        this.paymentStrategyManager = paymentStrategyManager;
    }

    @Override
    public void processPayment(Ride ride) {
        Payment payment =
                paymentRepository.findByRide(ride).orElseThrow(() -> new ResourceNotFoundException("Payment not found for ride with id: " + ride.getId()));
        paymentStrategyManager.paymentStrategy(payment.getPaymentMethod()).processPayment(payment);
    }

    @Override
    public Payment createNewPayment(Ride ride) {
        Payment payment = new Payment();
        payment.setRide(ride);
        payment.setPaymentMethod(ride.getPaymentMethod());
        payment.setAmount(ride.getFare());
        payment.setPaymentStatus(Status.PENDING);

        return paymentRepository.save(payment);
    }

    @Override
    public void updatePaymentStatus(Payment payment, Status status) {
        payment.setPaymentStatus(status);
        paymentRepository.save(payment);
    }
}
