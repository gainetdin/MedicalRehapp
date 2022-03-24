package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;

public interface PrescriptionManager {

    void createPrescriptionAndEvents(PrescriptionDto prescriptionDto);

    void updatePrescriptionAndEvents(PrescriptionDto prescriptionDto);

    void cancelPrescriptionAndEvents(String uuid);
}
