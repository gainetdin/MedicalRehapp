package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.PatientDto;

public interface PatientService {

    void create(PatientDto patientDto);

    void update(PatientDto patientDto);

    PatientDto findByInsuranceNumber(String insuranceNumber);

    void discharge(PatientDto patientDto);
}
