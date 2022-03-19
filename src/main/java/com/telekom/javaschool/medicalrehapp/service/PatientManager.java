package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.PatientDto;

public interface PatientManager {

    void dischargePatientAndCancelEverything(String insuranceNumber);
}
