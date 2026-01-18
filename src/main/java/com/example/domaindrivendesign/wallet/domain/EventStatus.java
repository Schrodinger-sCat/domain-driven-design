package com.example.domaindrivendesign.wallet.domain;

public enum EventStatus {
  PENDING(1L, "Pending"),
  PROCESSING(2L, "Processing"),
  COMPLETED(3L, "Completed"),
  FAILED(4L, "Failed");

  private final Long id;
  private final String description;

  EventStatus(Long id, String description) {
    this.id = id;
    this.description = description;
  }

  public Long getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public static EventStatus fromId(Long id) {
    for (EventStatus status : EventStatus.values()) {
      if (status.id.equals(id)) {
        return status;
      }
    }
    throw new IllegalArgumentException("Unknown EventStatus id: " + id);
  }
}
