package com.codingshuttle.project.uber.uberApp.strategies.impl;

import com.codingshuttle.project.uber.uberApp.entities.Driver;
import com.codingshuttle.project.uber.uberApp.entities.Payment;
import com.codingshuttle.project.uber.uberApp.entities.Wallet;
import com.codingshuttle.project.uber.uberApp.entities.enums.Status;
import com.codingshuttle.project.uber.uberApp.entities.enums.TransactionMethod;
import com.codingshuttle.project.uber.uberApp.repositories.PaymentRepository;
import com.codingshuttle.project.uber.uberApp.services.WalletService;
import com.codingshuttle.project.uber.uberApp.strategies.PaymentStrategy;
import org.springframework.stereotype.Service;

@Service
public class CashPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    public CashPaymentStrategy(WalletService walletService, PaymentRepository paymentRepository) {
        this.walletService = walletService;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();

        Double platformCommision = payment.getAmount() * PLATFORM_COMMISION;

        walletService.deductMoneyFromWallet(driver.getUser(), platformCommision, null,
                payment.getRide(), TransactionMethod.RIDE);

        payment.setPaymentStatus(Status.CONFIRMED);
        paymentRepository.save(payment);
    }
}
