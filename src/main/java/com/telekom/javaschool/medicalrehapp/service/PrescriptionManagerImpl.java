package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrescriptionManagerImpl implements PrescriptionManager {

    private final PrescriptionService prescriptionService;
    private final EventService eventService;

    @Autowired
    public PrescriptionManagerImpl(PrescriptionService prescriptionService, EventService eventService) {
        this.prescriptionService = prescriptionService;
        this.eventService = eventService;
    }

    @Override
    @Transactional
    public void createPrescriptionAndEvents(PrescriptionDto prescriptionDto) {
        PrescriptionEntity prescriptionEntity = prescriptionService.create(prescriptionDto);
        eventService.create(prescriptionEntity);
    }

    @Override
    @Transactional
    public void updatePrescriptionAndEvents(PrescriptionDto prescriptionDto) {
        PrescriptionEntity prescriptionEntity = prescriptionService.update(prescriptionDto);
        eventService.create(prescriptionEntity);
    }

    @Override
    @Transactional
    public void cancelPrescriptionAndEvents(String uuid) {
        PrescriptionEntity prescriptionEntity = prescriptionService.cancelByUuid(uuid);
        eventService.cancelByPrescription(prescriptionEntity);
    }
}
