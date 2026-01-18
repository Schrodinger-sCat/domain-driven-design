package com.example.domaindrivendesign.wallet.dto;

import com.example.domaindrivendesign.wallet.domain.Wallet;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WalletCoreBusinessRequestDto {
  Wallet wallet;
  double depositAmount;
  double withdrawAmount;
}
