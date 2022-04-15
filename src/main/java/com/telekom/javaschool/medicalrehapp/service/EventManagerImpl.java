package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.EventBoardDto;
import com.telekom.javaschool.medicalrehapp.dto.EventDto;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventManagerImpl implements EventManager {

    private final EventService eventService;
    private final MessageService messageService;

    @Autowired
    public EventManagerImpl(EventService eventService, MessageService messageService) {
        this.eventService = eventService;
        this.messageService = messageService;
    }

    @Override
    public void sendEvents() {
        List<EventBoardDto> eventBoardDtoList = eventService.getBoardEvents();
        messageService.sendMessage(eventBoardDtoList);
    }

    @Override
    public void updateEvent(EventDto eventDto) {
        eventService.update(eventDto);
        sendEvents();
    }

    @Override
    public void cancelEventsByPrescription(PrescriptionEntity prescriptionEntity) {
        eventService.cancelByPrescription(prescriptionEntity);
        sendEvents();
    }

    @Override
    public void createEvents(PrescriptionEntity prescriptionEntity) {
        eventService.create(prescriptionEntity);
        sendEvents();
    }

    @Override
    public void updateEvents(PrescriptionEntity prescriptionEntity) {
        eventService.updateByPrescription(prescriptionEntity);
        sendEvents();
    }

    @Override
    public void cancelEventsByPatient(PatientEntity patientEntity) {
        eventService.cancelByPatient(patientEntity);
        sendEvents();
    }
}
