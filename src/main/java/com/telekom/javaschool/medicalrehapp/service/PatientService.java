package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.PatientDto;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;

import java.util.List;

public interface PatientService {

    void create(PatientDto patientDto);

    void update(PatientDto patientDto);

    List<PatientDto> findAll();

    PatientDto findByInsuranceNumber(String insuranceNumber);

    PatientEntity discharge(String insuranceNumber);

    PatientEntity takeBack(String insuranceNumber);
}
