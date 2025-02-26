package org.example.enums;

public enum ShipmentExpenseStatus {
    CREATED(1),
    APPROVED(2),
    REJECTED(3),
    PENDING(4),
    PAID(5);

    private final int value;

    ShipmentExpenseStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
