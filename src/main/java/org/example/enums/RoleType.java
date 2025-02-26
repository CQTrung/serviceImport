package org.example.enums;

public enum RoleType {

    ROLE_MASTER(1, "ROLE_MASTER", "MASTER"),
    ROLE_LEADER(2, "ROLE_LEADER", "LEADER"),
    ROLE_SENDER(3, "ROLE_SENDER", "SENDER"),
    ROLE_RECEIVER(4, "ROLE_RECEIVER", "RECEIVER"),
    ROLE_DRIVER(5, "ROLE_DRIVER", "DRIVER"),
    ROLE_USER(6, "ROLE_USER", "USER"),
    ROLE_REPORTER(7, "ROLE_REPORTER", "REPORTER"),
    ROLE_MASTER_TRACK_BILL(9, "ROLE_MASTER_TRACK_BILL", "MASTER_TRACK_BILL");

    private final int key;
    private final String value;
    private final String role;

    RoleType(int key, String value, String role) {
        this.key = key;
        this.value = value;
        this.role = role;
    }

    public String getValue() {
        return value;
    }

    public String getRole() {
        return role;
    }

    public int getKey() {
        return key;
    }
}
