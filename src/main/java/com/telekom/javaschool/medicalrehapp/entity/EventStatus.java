package com.telekom.javaschool.medicalrehapp.entity;

public enum EventStatus {
    SCHEDULED("Scheduled"),
    COMPLETED("Completed"),
    CANCELED("Canceled");

    private final String label;

    public String getLabel() {
        return label;
    }

    EventStatus(String label) {
        this.label = label;
    }
}
