package com.codingshuttle.project.uber.uberApp.services;

import com.codingshuttle.project.uber.uberApp.entities.Ride;
import com.codingshuttle.project.uber.uberApp.entities.User;
import com.codingshuttle.project.uber.uberApp.entities.Wallet;
import com.codingshuttle.project.uber.uberApp.entities.enums.TransactionMethod;

public interface WalletService {

    Wallet addMonetToWallet(User user, Double amount, String transactionId, Ride ride,
                            TransactionMethod transactionMethod);

    void withdrawAllMyMoneyFromWallet();

    Wallet findWalletById(Long walletId);

    Wallet createNewWallet(User user);

    Wallet findByUser(User user);

    Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, Ride ride,
                                 TransactionMethod transactionMethod);
}
