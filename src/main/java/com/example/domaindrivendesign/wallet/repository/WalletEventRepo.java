package com.example.domaindrivendesign.wallet.repository;

import com.example.domaindrivendesign.wallet.domain.WalletEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WalletEventRepo extends JpaRepository<WalletEvent, Long> {
  WalletEvent findById(long id);
  WalletEvent findByWalletId(long walletId);

  @Query("SELECT e FROM WalletEvent e WHERE e.statusId = :statusId ORDER BY e.createdAt ASC")
  List<WalletEvent> findByStatusId(@Param("statusId") Long statusId);

  @Query("SELECT e FROM WalletEvent e WHERE e.statusId IN :statusIds ORDER BY e.createdAt ASC")
  List<WalletEvent> findByStatusIdIn(@Param("statusIds") List<Long> statusIds);
}
