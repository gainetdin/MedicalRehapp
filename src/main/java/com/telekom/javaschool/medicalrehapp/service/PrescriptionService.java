package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface PrescriptionService {

    /**
     * Converts period to LocalDateTime format and maps DTO to entity object.
     * @param prescriptionDto prescription data transfer object
     * @return prescription entity
     */
    PrescriptionEntity prepareCreate(@Valid PrescriptionDto prescriptionDto);

    /**
     * Gets old prescription and changes modified information.
     * @param prescriptionDto prescription data transfer object
     * @return prescription entity
     */
    PrescriptionEntity prepareUpdate(@Valid PrescriptionDto prescriptionDto);

    /**
     * Sets entity status to ACTIVE and saves to repository.
     * @param prescriptionEntity prescription entity
     * @return modified prescription entity
     */
    PrescriptionEntity save(PrescriptionEntity prescriptionEntity);

    /**
     * Gets prescription by its UUID.
     * @param uuid unique identifier of prescription
     * @return prescription data transfer object
     */
    PrescriptionDto findByUuid(String uuid);

    /**
     * Checks patient's prescriptions if they should be completed by this time
     * and in this case changes prescription's status.
     * @param insuranceNumber patient's insurance number
     * @return list of prescriptions
     */
    List<PrescriptionDto> getAndCheckPrescriptionsByPatient(String insuranceNumber);


    /**
     * Cancels prescription by its UUID.
     * @param uuid unique identifier of prescription
     * @return prescription entity
     */
    PrescriptionEntity cancelByUuid(String uuid);

    /**
     * Cancels patient's all active prescriptions.
     * @param patientEntity patient entity
     */
    void cancelAllByPatient(PatientEntity patientEntity);
}
