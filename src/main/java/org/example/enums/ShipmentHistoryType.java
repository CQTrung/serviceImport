package org.example.enums;

public enum ShipmentHistoryType {
  TO_DO("To do"),
  ASSIGN_DRIVER("Assign driver"),
  NOT_STARTED("not started"),
  DRIVER_ACCEPTED("Driver accepted"),
  DRIVER_REJECTED("Driver rejected"),
  ADD_STAFF("Add staff"),
  ADD_EXPENSE("Add expense"),
  START("Start"),
  ON_DELIVERY("On delivery"),
  COMPLETED("Completed"),
  CLOSE("Close");

  private final String value;

  ShipmentHistoryType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  // Add a method to get the appropriate ShipmentHistoryType based on the status
  public static ShipmentHistoryType getActionForStatus(String status) {
    for (ShipmentHistoryType type : ShipmentHistoryType.values()) {
      if (type.getValue().equalsIgnoreCase(status)) {
        return type;
      }
    }
    // Return a default or throw an exception if no match is found
    throw new IllegalArgumentException("Invalid shipment status: " + status);
  }
}
