package com.telekom.javaschool.medicalrehapp.entity;

import javax.persistence.*;

@Entity
public class Patient extends AbstractEntity {

    private String name;
    private String diagnosis;
    @Column(unique = true)
    private String insuranceNumber; //format: XXX-XXX-XXX-XX
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    private PatientStatus patientStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public PatientStatus getPatientStatus() {
        return patientStatus;
    }

    public void setPatientStatus(PatientStatus patientStatus) {
        this.patientStatus = patientStatus;
    }

    public Patient() {
    }
}
