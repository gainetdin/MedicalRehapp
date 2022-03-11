package com.telekom.javaschool.medicalrehapp.dto;

import com.telekom.javaschool.medicalrehapp.entity.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto extends AbstractDto implements Serializable {
    private PatientDto patient;
    private LocalDateTime dateTime;
    private EventStatus eventStatus;
    private TreatmentDto treatment;
    private String cancelReason;
}
