package com.example.domaindrivendesign.wallet.repository;

import com.example.domaindrivendesign.wallet.domain.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletTransactionRepo extends JpaRepository<WalletTransaction, Long> {
}
