package com.telekom.javaschool.medicalrehapp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventBoardDto {

    private String patientName;

    private LocalDateTime dateTime;

    private String treatmentName;
}
