package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.EventDto;
import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    void create(PrescriptionEntity prescriptionEntity);

    void updateByPrescription(PrescriptionEntity prescriptionEntity);

    void update(EventDto eventDto);

    EventDto findByUuid(String uuid);

    void cancelByPrescription(PrescriptionEntity prescriptionEntity);

    void cancelByPatient(PatientEntity patientEntity);

    List<EventDto> showEventsByInsuranceNumber(String insuranceNumber);

    List<EventDto> showAllEvents();

    List<EventDto> showAllEventsFilteredByDateTime(LocalDateTime filterDateTime);
}
