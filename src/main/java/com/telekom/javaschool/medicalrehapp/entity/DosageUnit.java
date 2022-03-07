package com.telekom.javaschool.medicalrehapp.entity;

public enum DosageUnit {
    G("gram"),
    MG("milligram"),
    MCG("microgram"),
    L("litre"),
    ML("millilitre"),
    CC("cubic centimetre");

    private final String label;

    public String getLabel() {
        return label;
    }

    DosageUnit(String label) {
        this.label = label;
    }
}
