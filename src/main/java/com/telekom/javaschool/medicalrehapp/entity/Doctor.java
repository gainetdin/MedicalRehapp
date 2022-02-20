package com.telekom.javaschool.medicalrehapp.entity;

import javax.persistence.Entity;

@Entity
public class Doctor extends AbstractEntity {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Doctor() {
    }
}
