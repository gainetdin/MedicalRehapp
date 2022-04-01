package com.telekom.javaschool.medicalrehapp.dto;

import com.telekom.javaschool.medicalrehapp.entity.EventStatus;
import com.telekom.javaschool.medicalrehapp.validator.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
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

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTime;

    @EnumValue(enumClass = EventStatus.class)
    private EventStatus eventStatus;

    private TreatmentDto treatment;

    private String cancelReason;

    private PrescriptionDto prescription;
}
