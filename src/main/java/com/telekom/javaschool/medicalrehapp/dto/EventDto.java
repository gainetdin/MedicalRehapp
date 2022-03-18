package com.telekom.javaschool.medicalrehapp.dto;

import com.telekom.javaschool.medicalrehapp.entity.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDto extends AbstractDto implements Serializable {
    private UUID uuid;
    private PatientDto patient;
    private LocalDateTime dateTime;
    private EventStatus eventStatus;
    private TreatmentDto treatment;
    private String cancelReason;
    private PrescriptionDto prescription;
}
