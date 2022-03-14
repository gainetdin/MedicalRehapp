package com.telekom.javaschool.medicalrehapp.entity;

public enum TreatmentType {
    MEDICINE("Medicine"),
    PROCEDURE("Procedure");

    private final String label;

    public String getLabel() {
        return label;
    }

    TreatmentType(String label) {
        this.label = label;
    }
}
