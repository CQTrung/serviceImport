package org.example.enums;

public enum ShipmentHistoryTypeInt {
  TO_DO(1),
  ASSIGN_DRIVER(2),
  DRIVER_ACCEPTED(3),
  DRIVER_REJECTED(4),
  ADD_STAFF(5),
  ADD_EXPENSE(6),
  START(7),
  COMPLETED(8),
  CLOSE(9),
  ON_DELIVERY(10);

  private final Integer value;

  ShipmentHistoryTypeInt(Integer value) {
    this.value = value;
  }

  public Integer getValue() {
    return value;
  }
}
