package com.cs.project.uber.uberApp.services.impl;

import com.cs.project.uber.uberApp.entities.WalletTransaction;
import com.cs.project.uber.uberApp.repositories.WalletTransactionRepository;
import com.cs.project.uber.uberApp.services.WalletTransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final WalletTransactionRepository walletTransactionRepository;
    private final ModelMapper modelMapper;

    public WalletTransactionServiceImpl(WalletTransactionRepository walletTransactionRepository, ModelMapper modelMapper) {
        this.walletTransactionRepository = walletTransactionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createNewWalletTransaction(WalletTransaction walletTransaction) {
        walletTransactionRepository.save(walletTransaction);
    }
}
