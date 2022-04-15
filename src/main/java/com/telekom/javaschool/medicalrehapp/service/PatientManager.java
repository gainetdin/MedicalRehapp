package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.PatientDto;

public interface PatientManager {

    /**
     * Delegates patient discharging, his prescriptions canceling and events managing to related services.
     * @param insuranceNumber patient's insurance number
     */
    void dischargePatientAndCancelEverything(String insuranceNumber);

    /**
     * Delegates patient creating and doctor assigning to related services.
     * @param patientDto patient data transfer object
     */
    void createPatient(PatientDto patientDto);

    /**
     * Delegates patient updating and doctor assigning to related services.
     * @param patientDto patient data transfer object
     */
    void updatePatient(PatientDto patientDto);
}
