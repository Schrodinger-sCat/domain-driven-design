package com.example.domaindrivendesign.wallet.service;

import com.example.domaindrivendesign.wallet.domain.Wallet;
import com.example.domaindrivendesign.wallet.domain.WalletTransaction;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
  @Override
  public void validateWallet(Wallet wallet) {

  }

  @Override
  public WalletTransaction withdraw(Wallet wallet, double amount) {
    WalletTransaction walletTransaction = new WalletTransaction();
    walletTransaction.setWallet(wallet);
    walletTransaction.setWithdrawAmount(amount);

    return walletTransaction;
  }

  @Override
  public WalletTransaction deposit(Wallet wallet, double amount) {
    WalletTransaction walletTransaction = new WalletTransaction();
    walletTransaction.setWallet(wallet);
    walletTransaction.setDepositAmount(amount);

    return walletTransaction;
  }

  @Override
  public void setBalance(Wallet wallet, WalletTransaction walletTransaction) {
    wallet.setBalance(wallet.getBalance() + walletTransaction.getDepositAmount() - walletTransaction.getWithdrawAmount());
  }
}
