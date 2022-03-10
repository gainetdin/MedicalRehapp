package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;

import java.util.List;

public interface PrescriptionService {

    void create(PrescriptionDto prescriptionDto);

    void update(PrescriptionDto prescriptionDto);

    List<PrescriptionDto> findAll();

    PrescriptionDto findById(String id);

    List<PrescriptionDto> findPrescriptionsByPatient(String insuranceNumber);
}
