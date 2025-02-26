package org.example.enums;

public enum DocumentSecurityMaskType {
  READ(1),
  DELETE(2);

  private final int value;

  DocumentSecurityMaskType(int value) {
    this.value = value;
  }

  public int getValue() {
    return (short) value;
  }
}
