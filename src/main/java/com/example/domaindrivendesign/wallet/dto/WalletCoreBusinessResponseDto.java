package com.example.domaindrivendesign.wallet.dto;

import com.example.domaindrivendesign.wallet.domain.Wallet;
import com.example.domaindrivendesign.wallet.domain.WalletTransaction;
import lombok.Data;

@Data
public class WalletCoreBusinessResponseDto {
  Wallet wallet;
  WalletTransaction walletTransaction;
}
