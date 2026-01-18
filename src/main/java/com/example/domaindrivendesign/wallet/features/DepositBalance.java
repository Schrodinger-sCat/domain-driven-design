package com.example.domaindrivendesign.wallet.features;

import com.example.domaindrivendesign.wallet.aggregateroot.WalletCoreBusiness;
import com.example.domaindrivendesign.wallet.domain.WalletEvent;
import com.example.domaindrivendesign.wallet.domain.WalletEventPayload;
import com.example.domaindrivendesign.wallet.dto.WalletCoreBusinessResponseDto;
import com.example.domaindrivendesign.wallet.mapper.MapperUtil;
import com.example.domaindrivendesign.wallet.repository.WalletEventRepo;
import com.example.domaindrivendesign.wallet.repository.WalletRepo;
import com.example.domaindrivendesign.wallet.repository.WalletTransactionRepo;
import com.example.domaindrivendesign.wallet.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepositBalance {
  private final MapperUtil mapperUtil;
  private final WalletService walletService;
  private final WalletRepo walletRepo;
  private final WalletTransactionRepo walletTransactionRepo;
  private final WalletEventRepo walletEventRepo;

  public void deposit(WalletEvent walletEvent) {
    featureValidation(walletEvent.getPayload());

    WalletCoreBusiness walletCoreBusiness =
        new WalletCoreBusiness(walletService, mapperUtil.payloadToRequestDto(walletEvent.getPayload()))
            .validateWallet()
            .deposit()
            .setBalance();

    saveEntities(walletCoreBusiness.getResponseDto(), walletEvent);
  }
  public void featureValidation(WalletEventPayload payload) {
    //Feature wise business validation
  }

  public void saveEntities(WalletCoreBusinessResponseDto walletCoreBusinessResponseDto, WalletEvent walletEvent) {
    changeWalletEventStatus(walletEvent);
    walletRepo.save(walletCoreBusinessResponseDto.getWallet());
    walletTransactionRepo.save(walletCoreBusinessResponseDto.getWalletTransaction());
    walletEventRepo.save(walletEvent);
  }

  public void changeWalletEventStatus(WalletEvent walletEvent) {
    walletEvent.setStatusId(com.example.domaindrivendesign.wallet.domain.EventStatus.COMPLETED.getId());
  }
}
