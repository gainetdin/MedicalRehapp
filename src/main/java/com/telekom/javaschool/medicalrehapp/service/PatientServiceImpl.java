package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.PatientDto;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    @Override
    public void create(PatientDto patientDto) {

    }

    @Override
    public void update(PatientDto patientDto) {

    }

    @Override
    public PatientDto findByInsuranceNumber(String insuranceNumber) {
        return null;
    }

    @Override
    public void discharge(PatientDto patientDto) {

    }
}
