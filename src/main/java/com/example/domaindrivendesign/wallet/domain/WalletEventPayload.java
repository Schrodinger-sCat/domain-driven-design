package com.example.domaindrivendesign.wallet.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Embeddable
public class WalletEventPayload {
  @Column(name = "payload_wallet_id")
  private Long walletId;

  @Column(name = "payload_deposit_amount")
  private BigDecimal depositAmount;

  @Column(name = "payload_withdraw_amount")
  private BigDecimal withdrawAmount;

  @Column(name = "payload_transaction_id")
  private Long transactionId;

  @Column(name = "payload_wallet_event_id")
  private Long walletEventId;
}
