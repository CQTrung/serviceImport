package org.example.enums;

public enum DeliveryOrderStatus {
    UNASSIGNED(-1),
    TO_DO(1),
    CHECK_IN_START(2),
    CHECK_IN_FINISH(3),
    LOADING_START(4),
    LOADING_FINISH(5),
    LOADING_COMPLETED_START(6),
    LOADING_COMPLETED_FINISH(7),
    DELIVERY_FINISH(8),
    DELIVERY_REJECT(9),
    MOVED(10), 
    CLOSE_BILL_COMPLETED(11),//close bilcl
    CLOSE_BILL(12),//approved bill
    REJECT_DISABLE(13),
    REJECT(14),//reject by admin
    REJECT_SHOP(15),
    CHECK_IN_DELIVERY(16),
    CANCELLED(17),

    RECEIVE_BILL(18),

    UNLOAD_DELIVERY(19);
    

    private final int value;

    DeliveryOrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static String getNameByValue(int value) {
        for (DeliveryOrderStatus status : values()) {
            if (status.getValue() == value) {
                return status.name();
            }
        }
        throw new IllegalArgumentException("Invalid status value: " + value);
    }

    // Phương thức để ánh xạ từ Integer sang Enum
    public static DeliveryOrderStatus fromValue(int value) {
        for (DeliveryOrderStatus status : DeliveryOrderStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid DeliveryOrderStatus value: " + value);
    }
}
