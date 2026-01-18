package com.example.domaindrivendesign.wallet.service;

import com.example.domaindrivendesign.wallet.domain.Wallet;
import com.example.domaindrivendesign.wallet.domain.WalletTransaction;

public interface WalletService {
  public void validateWallet(Wallet wallet);
  public WalletTransaction withdraw(Wallet wallet, double amount);
  public WalletTransaction deposit(Wallet wallet, double amount);
  public void setBalance(Wallet wallet, WalletTransaction walletTransaction);
}
