package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.EventDto;
import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;

import java.util.List;

public interface EventService {

    void create(PrescriptionDto prescriptionDto);

    void update(PrescriptionDto prescriptionDto);

    void deleteByPrescription(PrescriptionDto prescriptionDto);

    List<EventDto> showAllEvents();

    List<EventDto> showEventsByPatient();

    List<EventDto> showEventsByTime();
}
