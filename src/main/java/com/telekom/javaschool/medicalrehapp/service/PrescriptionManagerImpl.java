package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionEntity;
import com.telekom.javaschool.medicalrehapp.entity.TimePatternEntity;
import com.telekom.javaschool.medicalrehapp.entity.TreatmentEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class PrescriptionManagerImpl implements PrescriptionManager {

    private final PrescriptionService prescriptionService;
    private final EventManager eventManager;
    private final PatientService patientService;
    private final TimePatternService timePatternService;
    private final TreatmentService treatmentService;

    @Autowired
    public PrescriptionManagerImpl(PrescriptionService prescriptionService,
                                   EventManager eventManager,
                                   PatientService patientService,
                                   TimePatternService timePatternService,
                                   TreatmentService treatmentService) {
        this.prescriptionService = prescriptionService;
        this.eventManager = eventManager;
        this.patientService = patientService;
        this.timePatternService = timePatternService;
        this.treatmentService = treatmentService;
    }

    @Override
    @Transactional
    public void createPrescriptionAndEvents(PrescriptionDto prescriptionDto) {
        PrescriptionEntity prescriptionEntity = prescriptionService.prepareCreate(prescriptionDto);
        setEntitiesAndSavePrescription(prescriptionDto, prescriptionEntity);
        log.info("Prescription created");
        eventManager.createEvents(prescriptionEntity);
    }

    @Override
    @Transactional
    public void updatePrescriptionAndEvents(PrescriptionDto prescriptionDto) {
        PrescriptionEntity prescriptionEntity = prescriptionService.prepareUpdate(prescriptionDto);
        setEntitiesAndSavePrescription(prescriptionDto, prescriptionEntity);
        log.info("Prescription updated");
        eventManager.updateEvents(prescriptionEntity);
    }

    @Override
    @Transactional
    public void cancelPrescriptionAndEvents(String uuid) {
        PrescriptionEntity prescriptionEntity = prescriptionService.cancelByUuid(uuid);
        eventManager.cancelEventsByPrescription(prescriptionEntity);
    }

    private void setEntitiesAndSavePrescription(PrescriptionDto prescriptionDto, PrescriptionEntity prescriptionEntity) {
        PatientEntity patientEntity = patientService.getByPrescription(prescriptionDto);
        TimePatternEntity timePatternEntity = timePatternService.create(prescriptionDto);
        TreatmentEntity treatmentEntity = treatmentService.getByPrescription(prescriptionDto);
        prescriptionEntity.setPatient(patientEntity);
        prescriptionEntity.setTimePattern(timePatternEntity);
        prescriptionEntity.setTreatment(treatmentEntity);
        prescriptionService.save(prescriptionEntity);
    }
}
