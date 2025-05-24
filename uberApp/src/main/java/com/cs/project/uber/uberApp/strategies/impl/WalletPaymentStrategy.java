package com.cs.project.uber.uberApp.strategies.impl;

import com.cs.project.uber.uberApp.entities.Driver;
import com.cs.project.uber.uberApp.entities.Payment;
import com.cs.project.uber.uberApp.entities.Rider;
import com.cs.project.uber.uberApp.entities.enums.Status;
import com.cs.project.uber.uberApp.entities.enums.TransactionMethod;
import com.cs.project.uber.uberApp.repositories.PaymentRepository;
import com.cs.project.uber.uberApp.services.WalletService;
import com.cs.project.uber.uberApp.strategies.PaymentStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    public WalletPaymentStrategy(WalletService walletService, PaymentRepository paymentRepository) {
        this.walletService = walletService;
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();;
        Rider rider = payment.getRide().getRider();

        walletService.deductMoneyFromWallet(rider.getUser(), payment.getAmount(), null,
                payment.getRide(), TransactionMethod.RIDE);

        Double driversCut = payment.getAmount() * (1 - PLATFORM_COMMISION);

        walletService.addMonetToWallet(driver.getUser(), driversCut, null, payment.getRide(),
                TransactionMethod.RIDE);

        payment.setPaymentStatus(Status.CONFIRMED);
        paymentRepository.save(payment);
    }
}
