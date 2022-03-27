package com.telekom.javaschool.medicalrehapp.dto;

import com.telekom.javaschool.medicalrehapp.entity.DosageUnit;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionStatus;
import com.telekom.javaschool.medicalrehapp.validator.CreateMarker;
import com.telekom.javaschool.medicalrehapp.validator.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionDto extends AbstractDto implements Serializable {

    private UUID uuid;

    @NotNull
    private PatientDto patient;

    @NotNull
    private TreatmentDto treatment;

    @NotNull
    private TimePatternDto timePattern;

    private LocalDateTime startDateTime;

    @Future
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @Min(value = 1, groups = CreateMarker.class)
    @Max(value = 30, groups = CreateMarker.class)
    private int periodValue;

    @EnumValue(enumClass = ChronoUnit.class, groups = CreateMarker.class)
    private ChronoUnit periodUnit;

    @Min(0)
    @Max(100_000_000)
    private double dosage;

    @EnumValue(enumClass = DosageUnit.class)
    private DosageUnit dosageUnit;

    private PrescriptionStatus prescriptionStatus;
}
