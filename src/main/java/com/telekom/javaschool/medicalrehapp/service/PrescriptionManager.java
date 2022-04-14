package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;

public interface PrescriptionManager {

    /**
     * Delegates prescription and events creating to related services.
     * @param prescriptionDto prescription data transfer object
     */
    void createPrescriptionAndEvents(PrescriptionDto prescriptionDto);

    /**
     * Delegates prescription updating and events canceling/creating to related services.
     * @param prescriptionDto prescription data transfer object
     */
    void updatePrescriptionAndEvents(PrescriptionDto prescriptionDto);

    /**
     * Delegates prescription and events canceling to related services.
     * @param uuid unique identifier of prescription
     */
    void cancelPrescriptionAndEvents(String uuid);
}
