package com.example.domaindrivendesign.wallet.scheduler;

public interface OutboxHandler {
  public void OutboxHandle();
  public void insertIntoQueue();
}
