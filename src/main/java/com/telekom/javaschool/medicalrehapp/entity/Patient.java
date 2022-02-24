package com.telekom.javaschool.medicalrehapp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "patient")
public class Patient extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "insurance_number", unique = true)
    private String insuranceNumber; //format: XXX-XXX-XXX-XX

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Enumerated
    @Column(name = "patient_status")
    private PatientStatus patientStatus;

}
