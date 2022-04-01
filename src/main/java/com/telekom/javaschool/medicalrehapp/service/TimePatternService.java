package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.entity.TimePatternEntity;

public interface TimePatternService {

    TimePatternEntity create(PrescriptionDto prescriptionDto);
}
