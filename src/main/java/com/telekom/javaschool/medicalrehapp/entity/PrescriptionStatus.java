package com.telekom.javaschool.medicalrehapp.entity;

public enum PrescriptionStatus {
    ACTIVE("Active"),
    COMPLETED("Completed"),
    CANCELED("Canceled");

    private final String label;

    public String getLabel() {
        return label;
    }

    PrescriptionStatus(String label) {
        this.label = label;
    }
}
