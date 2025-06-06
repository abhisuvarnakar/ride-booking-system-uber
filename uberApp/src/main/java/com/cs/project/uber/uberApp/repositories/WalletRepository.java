package com.cs.project.uber.uberApp.repositories;

import com.cs.project.uber.uberApp.entities.User;
import com.cs.project.uber.uberApp.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUser(User user);
}
