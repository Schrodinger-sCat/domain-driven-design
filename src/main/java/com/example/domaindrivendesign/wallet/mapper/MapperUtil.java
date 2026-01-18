package com.example.domaindrivendesign.wallet.mapper;

import com.example.domaindrivendesign.wallet.domain.WalletEventPayload;
import com.example.domaindrivendesign.wallet.domain.WalletTransaction;
import com.example.domaindrivendesign.wallet.dto.WalletCoreBusinessRequestDto;
import com.example.domaindrivendesign.wallet.repository.WalletRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MapperUtil {
  private final WalletRepo walletRepo;

  public WalletCoreBusinessRequestDto payloadToRequestDto(WalletEventPayload payload) {

    return new WalletCoreBusinessRequestDto(
        walletRepo.findById(payload.getWalletId()),
        payload.getDepositAmount(),
        payload.getWithdrawAmount()
    );
  }
}
