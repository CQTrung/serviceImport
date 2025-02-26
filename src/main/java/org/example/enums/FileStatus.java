package org.example.enums;

public enum FileStatus {
    PENDING(1),
    PROCESSING(2),
    FINISH(3);

    private final int value;

    FileStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static FileStatus fromValue(int value) {
        for (FileStatus status : FileStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status value: " + value);
    }
}