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

    void create(@Valid PatientDto patientDto, DoctorEntity doctorEntity);

    void update(@Valid PatientDto patientDto, DoctorEntity doctorEntity);

    List<PatientDto> findAll();

    PatientDto getByInsuranceNumber(String insuranceNumber);

    PatientEntity getByPrescription(PrescriptionDto prescriptionDto);

    PatientEntity discharge(String insuranceNumber);

    PatientEntity takeBack(String insuranceNumber);
}
