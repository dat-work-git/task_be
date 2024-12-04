package com.project.salemanagement.models;

public enum Status {
    NOT_STARTED("Not Started"),
    DOING("Doing"),
    COMPLETED("Completed"),
    CANCELED("Canceled");

    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
    public static Status fromDisplayName(String displayName) {
        for (Status status : Status.values()) {
            if (status.getDisplayName().equalsIgnoreCase(displayName)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status display name: " + displayName);
    }
}
