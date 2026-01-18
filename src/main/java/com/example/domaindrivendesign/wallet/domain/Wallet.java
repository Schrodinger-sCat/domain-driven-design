package com.example.domaindrivendesign.wallet.domain;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Wallet {
  long id;
  double balance;
}
