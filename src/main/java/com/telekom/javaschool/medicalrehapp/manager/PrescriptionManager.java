package com.telekom.javaschool.medicalrehapp.manager;

import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionEntity;
import com.telekom.javaschool.medicalrehapp.service.EventService;
import com.telekom.javaschool.medicalrehapp.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrescriptionManager {

    private final PrescriptionService prescriptionService;
    private final EventService eventService;

    @Autowired
    public PrescriptionManager(PrescriptionService prescriptionService, EventService eventService) {
        this.prescriptionService = prescriptionService;
        this.eventService = eventService;
    }

    @Transactional
    public void createPrescriptionAndEvents(PrescriptionDto prescriptionDto) {
        PrescriptionEntity prescriptionEntity = prescriptionService.create(prescriptionDto);
        eventService.create(prescriptionEntity);
    }

    @Transactional
    public void updatePrescriptionAndEvents(PrescriptionDto prescriptionDto) {
        PrescriptionEntity prescriptionEntity = prescriptionService.update(prescriptionDto);
        eventService.create(prescriptionEntity);
    }
}
