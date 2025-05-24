package com.cs.project.uber.uberApp.services.impl;

import com.cs.project.uber.uberApp.entities.Ride;
import com.cs.project.uber.uberApp.entities.User;
import com.cs.project.uber.uberApp.entities.Wallet;
import com.cs.project.uber.uberApp.entities.WalletTransaction;
import com.cs.project.uber.uberApp.entities.enums.TransactionMethod;
import com.cs.project.uber.uberApp.entities.enums.TransactionType;
import com.cs.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.cs.project.uber.uberApp.repositories.WalletRepository;
import com.cs.project.uber.uberApp.services.WalletService;
import com.cs.project.uber.uberApp.services.WalletTransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletTransactionService walletTransactionService;
    private final ModelMapper modelMapper;

    public WalletServiceImpl(WalletRepository walletRepository, WalletTransactionService walletTransactionService, ModelMapper modelMapper) {
        this.walletRepository = walletRepository;
        this.walletTransactionService = walletTransactionService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public Wallet addMonetToWallet(User user, Double amount, String transactionId, Ride ride,
                                   TransactionMethod transactionMethod) {
        Wallet wallet = findByUser(user);
        wallet.setBalance(wallet.getBalance() + amount);

        WalletTransaction walletTransaction = getWalletTransaction(user, amount, transactionId,
                ride, transactionMethod, wallet, TransactionType.CREDIT);

        walletTransactionService.createNewWalletTransaction(walletTransaction);

        return walletRepository.save(wallet);
    }

    @Override
    public void withdrawAllMyMoneyFromWallet() {

    }

    @Override
    public Wallet findWalletById(Long walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found with " +
                        "id: " + walletId));
    }

    @Override
    public Wallet createNewWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findByUser(User user) {
        return walletRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Wallet not found for user with id: " + user.getId()));
    }

    @Override
    public Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, Ride ride,
                                        TransactionMethod transactionMethod) {
        Wallet wallet = findByUser(user);
        wallet.setBalance(wallet.getBalance() - amount);

        WalletTransaction walletTransaction = getWalletTransaction(user, amount, transactionId,
                ride, transactionMethod, wallet, TransactionType.DEBIT);


        wallet.getTransactions().add(walletTransaction);
        return walletRepository.save(wallet);
    }

    private WalletTransaction getWalletTransaction(User user, Double amount, String transactionId, Ride ride,
                                                   TransactionMethod transactionMethod,
                                                   Wallet wallet, TransactionType transactionType) {

        WalletTransaction walletTransaction = new WalletTransaction();
        walletTransaction.setTransactionId(transactionId);
        walletTransaction.setRide(ride);
        walletTransaction.setWallet(wallet);
        walletTransaction.setTransactionType(transactionType);
        walletTransaction.setTransactionMethod(transactionMethod);
        walletTransaction.setAmount(amount);

        return walletTransaction;
    }
}
