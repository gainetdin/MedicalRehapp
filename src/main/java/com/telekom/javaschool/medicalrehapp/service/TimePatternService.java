package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.entity.TimePatternEntity;

public interface TimePatternService {

    /**
     * Creates time pattern and time pattern elements from prescription DTO and saves to repository.
     * @param prescriptionDto prescription data transfer object
     * @return time pattern entity
     */
    TimePatternEntity create(PrescriptionDto prescriptionDto);
}
