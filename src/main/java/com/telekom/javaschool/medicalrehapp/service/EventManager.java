package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.EventDto;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionEntity;

public interface EventManager {

    /**
     * Gets events for information board and sends message to queue.
     */
    void sendEvents();

    /**
     * Delegates event updating to event service and message sending to message service.
     * @param eventDto event data transfer object with changed status
     */
    void updateEvent(EventDto eventDto);

    /**
     * Delegates events canceling by prescription to event service and message sending to message service.
     * @param prescriptionEntity modified prescription entity
     */
    void cancelEventsByPrescription(PrescriptionEntity prescriptionEntity);

    /**
     * Delegates events creating to event service and message sending to message service.
     * @param prescriptionEntity modified prescription entity
     */
    void createEvents(PrescriptionEntity prescriptionEntity);

    /**
     * Delegates events updating to event service and message sending to message service.
     * @param prescriptionEntity modified prescription entity
     */
    void updateEvents(PrescriptionEntity prescriptionEntity);

    /**
     * Delegates events canceling by patient's discharge to event service and message sending to message service.
     * @param patientEntity modified patient entity
     */
    void cancelEventsByPatient(PatientEntity patientEntity);
}
