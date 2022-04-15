package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.EventBoardDto;
import com.telekom.javaschool.medicalrehapp.dto.EventDto;
import com.telekom.javaschool.medicalrehapp.dto.EventRequestDto;
import com.telekom.javaschool.medicalrehapp.dto.EventResponseDto;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface EventService {

    /**
     * Creates events by prescription period and time pattern.
     * @param prescriptionEntity new prescription entity
     */
    void create(@Valid PrescriptionEntity prescriptionEntity);

    /**
     * Updates events by prescription period and time pattern, cancels old events.
     * @param prescriptionEntity updated prescription entity
     */
    void updateByPrescription(PrescriptionEntity prescriptionEntity);


    /**
     * Updates event by changing status.
     * @param eventDto event data transfer object with changed status
     */
    void update(@Valid EventDto eventDto);


    /**
     * Finds event by its UUID.
     * @param uuid unique identifier of event
     * @return event data transfer object
     */
    EventDto findByUuid(String uuid);


    /**
     * Cancels scheduled events of this prescription.
     * @param prescriptionEntity prescription entity
     */
    void cancelByPrescription(PrescriptionEntity prescriptionEntity);

    /**
     * Cancels scheduled events of this patient.
     * @param patientEntity patient entity
     */
    void cancelByPatient(PatientEntity patientEntity);


    /**
     * Gets list of events from repository by filter parameters.
     * @param eventRequestDto object with filter parameters (time boundaries, statuses, patient name,
     *                        page number, elements number on page)
     * @return object with page information (number of elements) and list of filtered events
     */
    EventResponseDto showEventsByFilters(EventRequestDto eventRequestDto);


    /**
     * Gets all of today's scheduled events for information board.
     * @return list of events for serialization to JSON
     */
    List<EventBoardDto> getBoardEvents();
}
