package com.telekom.javaschool.medicalrehapp.dto;

import com.telekom.javaschool.medicalrehapp.dto.DoctorDto;
import com.telekom.javaschool.medicalrehapp.entity.PatientStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto extends AbstractDto implements Serializable {
    private String name;
    private String diagnosis;
    private String insuranceNumber;
    private DoctorDto doctor;
    private PatientStatus patientStatus;
}
