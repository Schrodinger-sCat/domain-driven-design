package com.example.domaindrivendesign.wallet.aggregateroot;

import com.example.domaindrivendesign.wallet.dto.WalletCoreBusinessRequestDto;
import com.example.domaindrivendesign.wallet.dto.WalletCoreBusinessResponseDto;
import com.example.domaindrivendesign.wallet.service.WalletService;
import lombok.Data;

@Data
public class WalletCoreBusiness {
  private final WalletService walletService;
  private final WalletCoreBusinessRequestDto requestDto;
  private WalletCoreBusinessResponseDto responseDto;


  public WalletCoreBusiness(WalletService walletService, WalletCoreBusinessRequestDto requestDto) {
    this.walletService = walletService;
    this.requestDto = requestDto;
  }

  public WalletCoreBusiness validateWallet() {
    //validations
    return this;
  }

  public WalletCoreBusiness withdraw() {
    walletService.withdraw(requestDto.getWallet(), requestDto.getWithdrawAmount());
    return this;
  }

  public WalletCoreBusiness deposit() {
    walletService.deposit(requestDto.getWallet(), requestDto.getDepositAmount());
    return this;
  }

  public WalletCoreBusiness setBalance() {
    walletService.setBalance(requestDto.getWallet(), responseDto.getWalletTransaction());
    return this;
  }
}
