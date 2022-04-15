package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.dto.TreatmentDto;
import com.telekom.javaschool.medicalrehapp.entity.TreatmentEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface TreatmentService {

    /**
     * Creates new treatment.
     * @param treatmentDto treatment data transfer object
     */
    void create(@Valid TreatmentDto treatmentDto);

    /**
     * Gets list of all treatments from repository.
     * @return list of treatments
     */
    List<TreatmentDto> findAll();

    /**
     * Delete treatment by name.
     * @param name treatment name
     */
    void delete(String name);

    /**
     * Gets treatment entity by prescription.
     * @param prescriptionDto prescription data transfer object
     * @return treatment entity
     */
    TreatmentEntity getByPrescription(PrescriptionDto prescriptionDto);
}
