package com.telekom.javaschool.medicalrehapp.entity;

import javax.persistence.Entity;

@Entity
public class Treatment extends AbstractEntity {

    private String name;
    private TreatmentType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TreatmentType getType() {
        return type;
    }

    public void setType(TreatmentType type) {
        this.type = type;
    }

    public Treatment() {
    }
}
