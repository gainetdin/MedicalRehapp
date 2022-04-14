package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.EventDto;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionEntity;

public interface EventManager {

    void updateEvent(EventDto eventDto);

    void sendInitialEvents();

    void cancelEventsByPrescription(PrescriptionEntity prescriptionEntity);

    void createEvents(PrescriptionEntity prescriptionEntity);

    void updateEvents(PrescriptionEntity prescriptionEntity);

    void cancelEventsByPatient(PatientEntity patientEntity);
}
