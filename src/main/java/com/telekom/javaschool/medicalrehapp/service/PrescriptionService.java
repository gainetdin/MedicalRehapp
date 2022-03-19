package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionEntity;

import java.util.List;

public interface PrescriptionService {

    PrescriptionEntity create(PrescriptionDto prescriptionDto);

    PrescriptionEntity update(PrescriptionDto prescriptionDto);

    PrescriptionDto findByUuid(String uuid);

    List<PrescriptionDto> getAndCheckPrescriptionsByPatient(String insuranceNumber);

    PrescriptionEntity cancelByUuid(String uuid);

    void cancelAllByPatient(PatientEntity patientEntity);
}
