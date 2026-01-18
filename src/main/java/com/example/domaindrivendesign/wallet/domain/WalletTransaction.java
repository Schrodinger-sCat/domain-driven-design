package com.example.domaindrivendesign.wallet.domain;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class WalletTransaction {
  long id;
  double depositAmount;
  double withdrawAmount;
  @ManyToOne(fetch = FetchType.LAZY)
  Wallet wallet;
}
