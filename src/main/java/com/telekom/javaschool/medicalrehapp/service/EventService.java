package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.EventDto;
import com.telekom.javaschool.medicalrehapp.dto.EventResponseDto;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Validated
public interface EventService {

    void create(@Valid PrescriptionEntity prescriptionEntity);

    void updateByPrescription(PrescriptionEntity prescriptionEntity);

    void update(@Valid EventDto eventDto);

    EventDto findByUuid(String uuid);

    void cancelByPrescription(PrescriptionEntity prescriptionEntity);

    void cancelByPatient(PatientEntity patientEntity);

    List<EventDto> showEventsByInsuranceNumber(String insuranceNumber);

    EventResponseDto showAllEvents(int start, int length, int draw);

    List<EventDto> showAllEventsFilteredByDateTime(LocalDateTime filterDateTime);
}
