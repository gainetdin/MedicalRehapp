package com.telekom.javaschool.medicalrehapp.entity;

public enum PatientStatus {
    BEING_TREATED("Under treatment"),
    DISCHARGED("Discharged");

    private final String label;

    public String getLabel() {
        return label;
    }

    PatientStatus(String label) {
        this.label = label;
    }
}
