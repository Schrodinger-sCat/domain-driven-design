package com.example.domaindrivendesign.wallet.repository;

import com.example.domaindrivendesign.wallet.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface WalletRepo extends JpaRepository<Wallet, Long> {
  public Wallet findById(long id);
}
