package com.telekom.javaschool.medicalrehapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionDto implements Serializable {
    private PatientDto patient;
    private TreatmentDto treatment;
    private TimePatternDto timePattern;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private double dosage;
    private String dosageUnit;
}
