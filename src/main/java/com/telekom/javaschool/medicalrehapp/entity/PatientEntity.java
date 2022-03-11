package com.telekom.javaschool.medicalrehapp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "patient")
public class PatientEntity extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "insurance_number", unique = true, nullable = false)
    private String insuranceNumber; //format: XXX-XXX-XXX-XX

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private DoctorEntity doctor;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "patient_status", nullable = false)
    private PatientStatus patientStatus;

}
