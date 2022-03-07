package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.dto.TreatmentDto;

import java.util.List;

public interface TreatmentService {

    void create(TreatmentDto treatmentDto);

    void update(TreatmentDto treatmentDto);

    List<TreatmentDto> findAll();

    TreatmentDto findById(String id);
}
