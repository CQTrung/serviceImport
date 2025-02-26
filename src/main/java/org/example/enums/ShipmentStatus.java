package org.example.enums;

public enum ShipmentStatus {
    PLANING(1), // Moi import vao
    ASSIGNED(2), // Gan cho tai xe
    NOT_STARTED(3), // Tai xe da xac nhan
    STARTED(4), // Tai xe bat dau nhan hang o kho
    ON_DELIVERY(5), // Tai xe dang giao hang
    COMPLETED(6), // Tai xe xac nhan hoan thanh tat ca don
    CLOSED(7), // Admin/leader phe duyet don hang hoan thanh
    CLOSED_COMPETED(8), // Admin/leader phe duyet don hang hoan thanh
    DRIVER_ACCEPTED(9),
    DRIVER_REJECTED(10),
    ADD_STAFF(11),
    ADD_EXPENSE(12);

    private final int value;

    ShipmentStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public static String getNameByValue(int value) {
        for (ShipmentStatus status : values()) {
            if (status.getValue() == value) {
                return status.name();
            }
        }
        throw new IllegalArgumentException("Invalid status value: " + value);
    }
}
