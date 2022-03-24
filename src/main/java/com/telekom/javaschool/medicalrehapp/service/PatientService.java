package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.PatientDto;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface PatientService {

    void create(@Valid PatientDto patientDto);

    void update(@Valid PatientDto patientDto);

    List<PatientDto> findAll();

    PatientDto findByInsuranceNumber(String insuranceNumber);

    PatientEntity discharge(String insuranceNumber);

    PatientEntity takeBack(String insuranceNumber);
}
