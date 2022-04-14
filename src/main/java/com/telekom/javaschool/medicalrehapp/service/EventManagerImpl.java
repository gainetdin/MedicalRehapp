package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.EventBoardDto;
import com.telekom.javaschool.medicalrehapp.dto.EventDto;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void updateEvent(EventDto eventDto) {
        eventService.update(eventDto);
        updateBoard();
    }

    @Override
    @Transactional(readOnly = true)
    public void sendInitialEvents() {
        updateBoard();
    }

    @Override
    @Transactional
    public void cancelEventsByPrescription(PrescriptionEntity prescriptionEntity) {
        eventService.cancelByPrescription(prescriptionEntity);
        updateBoard();
    }

    @Override
    @Transactional
    public void createEvents(PrescriptionEntity prescriptionEntity) {
        eventService.create(prescriptionEntity);
        updateBoard();
    }

    @Override
    @Transactional
    public void updateEvents(PrescriptionEntity prescriptionEntity) {
        eventService.updateByPrescription(prescriptionEntity);
        updateBoard();
    }

    @Override
    @Transactional
    public void cancelEventsByPatient(PatientEntity patientEntity) {
        eventService.cancelByPatient(patientEntity);
        updateBoard();
    }

    private void updateBoard() {
        List<EventBoardDto> eventBoardDtoList = eventService.getBoardEvents();
        messageService.sendMessage(eventBoardDtoList);
    }
}
