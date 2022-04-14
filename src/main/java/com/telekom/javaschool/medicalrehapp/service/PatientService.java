package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.PatientDto;
import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.entity.DoctorEntity;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface PatientService {

    /**
     * Creates new patient.
     * @param patientDto    patient data transfer object with initial information
     * @param doctorEntity  patient's doctor entity
     */
    void create(@Valid PatientDto patientDto, DoctorEntity doctorEntity);

    /**
     * Updates patient information.
     * @param patientDto    patient data transfer object with modified information
     * @param doctorEntity  patient's doctor entity
     */
    void update(@Valid PatientDto patientDto, DoctorEntity doctorEntity);

    /**
     * Gets all patients from repository.
     * @return list of patient data transfer objects
     */
    List<PatientDto> findAll();

    /**
     * Gets patient by his insurance number.
     * @param insuranceNumber patient's insurance number
     * @return patient data transfer object
     */
    PatientDto getByInsuranceNumber(String insuranceNumber);

    /**
     * Gets patient by his prescription.
     * @param prescriptionDto prescription data transfer object
     * @return patient entity
     */
    PatientEntity getByPrescription(PrescriptionDto prescriptionDto);

    /**
     * Changes patient's status to DISCHARGED
     * @param insuranceNumber patient's insurance number
     * @return patient entity
     */
    PatientEntity discharge(String insuranceNumber);

    /**
     * Changes patient's status to BEING_TREATED
     * @param insuranceNumber patient's insurance number
     * @return patient entity
     */
    PatientEntity takeBack(String insuranceNumber);
}
