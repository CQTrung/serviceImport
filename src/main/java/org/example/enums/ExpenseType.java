package org.example.enums;

public enum ExpenseType {
  CREDIT_FUEL(1, "credit fuel"),
  CASH_FUEL(2, "cash fuel"),
  TOLL_FEES(3, "toll fees"),
  FINES(4, "fines"),
  RENTAL_FEES(5, "rental fees");
  private final int key;
  private final String value;

  ExpenseType(int key, String value) {
    this.key = key;
    this.value = value;
  }

  public int getKey() {
    return key;
  }
  public String getValue() {
    return value;
  }
}
