package com.codingshuttle.project.uber.uberApp.strategies;

import com.codingshuttle.project.uber.uberApp.entities.enums.PaymentMethod;
import com.codingshuttle.project.uber.uberApp.strategies.impl.CashPaymentStrategy;
import com.codingshuttle.project.uber.uberApp.strategies.impl.WalletPaymentStrategy;
import org.springframework.stereotype.Component;

@Component
public class PaymentStrategyManager {

    private final WalletPaymentStrategy walletPaymentStrategy;
    private final CashPaymentStrategy cashPaymentStrategy;

    public PaymentStrategyManager(WalletPaymentStrategy walletPaymentStrategy, CashPaymentStrategy cashPaymentStrategy) {
        this.walletPaymentStrategy = walletPaymentStrategy;
        this.cashPaymentStrategy = cashPaymentStrategy;
    }

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod) {
        return switch (paymentMethod) {
            case WALLET -> walletPaymentStrategy;
            case CASH -> cashPaymentStrategy;
            default -> throw new RuntimeException("Invalid payment method.");
        };
    }
}
