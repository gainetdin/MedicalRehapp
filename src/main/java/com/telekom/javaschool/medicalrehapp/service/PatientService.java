package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.PatientDto;

import java.util.List;

public interface PatientService {

    void create(PatientDto patientDto);

    void update(PatientDto patientDto);

    List<PatientDto> findAll();

    PatientDto findByInsuranceNumber(String insuranceNumber);

    void discharge(PatientDto patientDto);
}
