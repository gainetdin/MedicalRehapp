package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface PrescriptionService {

    PrescriptionEntity prepareCreate(@Valid PrescriptionDto prescriptionDto);

    PrescriptionEntity prepareUpdate(@Valid PrescriptionDto prescriptionDto);

    PrescriptionEntity save(PrescriptionEntity prescriptionEntity);

    PrescriptionDto findByUuid(String uuid);

    List<PrescriptionDto> getAndCheckPrescriptionsByPatient(String insuranceNumber);

    PrescriptionEntity cancelByUuid(String uuid);

    void cancelAllByPatient(PatientEntity patientEntity);
}
