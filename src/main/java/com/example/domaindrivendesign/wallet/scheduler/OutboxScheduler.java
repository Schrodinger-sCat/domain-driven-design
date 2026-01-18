package com.example.domaindrivendesign.wallet.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class OutboxScheduler {
  private final OutboxHandlerImpl outboxHandler;

  /**
   * Scheduled job that runs every 10 seconds to:
   * 1. Fetch pending events from the outbox (WalletEvent table)
   * 2. Insert them into the processing queue
   */
  @Scheduled(fixedDelayString = "${outbox.scheduler.fixed-delay:10000}")
  public void processOutboxEvents() {
    log.info("Outbox scheduler triggered - processing outbox events");
    try {
      // Fetch pending events and insert into queue
      outboxHandler.OutboxHandle();

      // Process events from the queue
      outboxHandler.insertIntoQueue();

      log.info("Outbox scheduler completed - queue size: {}", outboxHandler.getQueueSize());
    } catch (Exception e) {
      log.error("Error in outbox scheduler: {}", e.getMessage(), e);
    }
  }

  /**
   * Alternative scheduler for queue processing only (optional)
   * Can be used to separate queue fetching from queue processing
   */
  @Scheduled(fixedDelayString = "${outbox.queue.processor.fixed-delay:5000}")
  public void processQueueEvents() {
    log.debug("Queue processor triggered");
    try {
      outboxHandler.insertIntoQueue();
    } catch (Exception e) {
      log.error("Error in queue processor: {}", e.getMessage(), e);
    }
  }
}
