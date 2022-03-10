package com.telekom.javaschool.medicalrehapp.dto;

import com.telekom.javaschool.medicalrehapp.entity.DosageUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionDto extends AbstractDto implements Serializable {
    private UUID uuid;
    private PatientDto patient;
    private TreatmentDto treatment;
    private TimePatternDto timePattern;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int periodValue;
    private ChronoUnit periodUnit;
    private double dosage;
    private DosageUnit dosageUnit;
}
