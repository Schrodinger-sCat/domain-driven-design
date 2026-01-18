package com.example.domaindrivendesign.wallet.domain;

public enum EventType {
  DEPOSIT("DEPOSIT"),
  WITHDRAW("WITHDRAW");

  private final String type;

  EventType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
