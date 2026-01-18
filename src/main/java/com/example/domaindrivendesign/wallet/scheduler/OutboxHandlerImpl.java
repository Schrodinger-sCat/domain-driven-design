package com.example.domaindrivendesign.wallet.scheduler;

import com.example.domaindrivendesign.wallet.domain.EventStatus;
import com.example.domaindrivendesign.wallet.domain.EventType;
import com.example.domaindrivendesign.wallet.domain.WalletEvent;
import com.example.domaindrivendesign.wallet.features.DepositBalance;
import com.example.domaindrivendesign.wallet.features.WithdrawBalance;
import com.example.domaindrivendesign.wallet.repository.WalletEventRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@AllArgsConstructor
@Slf4j
public class OutboxHandlerImpl implements OutboxHandler {
  private final WalletEventRepo walletEventRepo;
  private final DepositBalance depositBalance;
  private final WithdrawBalance withdrawBalance;

  // In-memory queue for demo purposes
  private static final BlockingQueue<WalletEvent> eventQueue = new LinkedBlockingQueue<>();

  @Override
  @Transactional
  public void OutboxHandle() {
    log.info("Starting outbox handler - fetching pending events");

    // Fetch pending events from the outbox (WalletEvent table)
    List<WalletEvent> pendingEvents = walletEventRepo.findByStatusId(EventStatus.PENDING.getId());

    log.info("Found {} pending events to process", pendingEvents.size());

    for (WalletEvent event : pendingEvents) {
      try {
        // Mark as processing
        event.setStatusId(EventStatus.PROCESSING.getId());
        walletEventRepo.save(event);

        // Insert into queue for processing
        insertIntoQueue(event);

        log.info("Event {} inserted into queue for processing", event.getId());
      } catch (Exception e) {
        log.error("Error inserting event {} into queue: {}", event.getId(), e.getMessage(), e);
        handleEventFailure(event, e.getMessage());
      }
    }
  }

  @Override
  public void insertIntoQueue() {
    // Process events from the queue
    try {
      while (!eventQueue.isEmpty()) {
        WalletEvent event = eventQueue.poll();
        if (event != null) {
          processEvent(event);
        }
      }
    } catch (Exception e) {
      log.error("Error processing events from queue: {}", e.getMessage(), e);
    }
  }

  public void insertIntoQueue(WalletEvent event) {
    try {
      eventQueue.put(event);
      log.info("Event {} added to processing queue", event.getId());
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      log.error("Error adding event {} to queue: {}", event.getId(), e.getMessage(), e);
      throw new RuntimeException("Failed to insert event into queue", e);
    }
  }

  @Transactional
  public void processEvent(WalletEvent event) {
    try {
      log.info("Processing event {} of type {}", event.getId(), event.getEventType());

      // Route to appropriate feature based on event type
      if (event.getEventType() == EventType.DEPOSIT) {
        depositBalance.deposit(event);
      } else if (event.getEventType() == EventType.WITHDRAW) {
        withdrawBalance.withdraw(event);
      } else {
        throw new IllegalArgumentException("Unknown event type: " + event.getEventType());
      }

      // Mark as completed
      event.setStatusId(EventStatus.COMPLETED.getId());
      walletEventRepo.save(event);

      log.info("Event {} processed successfully", event.getId());
    } catch (Exception e) {
      log.error("Error processing event {}: {}", event.getId(), e.getMessage(), e);
      handleEventFailure(event, e.getMessage());
    }
  }

  @Transactional
  protected void handleEventFailure(WalletEvent event, String errorMessage) {
    event.setRetryCount(event.getRetryCount() + 1);
    event.setErrorMessage(errorMessage);

    // Mark as failed after 3 retries
    if (event.getRetryCount() >= 3) {
      event.setStatusId(EventStatus.FAILED.getId());
      log.error("Event {} marked as FAILED after {} retries", event.getId(), event.getRetryCount());
    } else {
      // Reset to pending for retry
      event.setStatusId(EventStatus.PENDING.getId());
      log.warn("Event {} retry count: {}", event.getId(), event.getRetryCount());
    }

    walletEventRepo.save(event);
  }

  public int getQueueSize() {
    return eventQueue.size();
  }
}
