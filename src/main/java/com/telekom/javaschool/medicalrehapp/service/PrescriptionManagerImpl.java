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
    private final EventService eventService;
    private final PatientService patientService;
    private final TimePatternService timePatternService;
    private final TreatmentService treatmentService;

    @Autowired
    public PrescriptionManagerImpl(PrescriptionService prescriptionService,
                                   EventService eventService,
                                   PatientService patientService,
                                   TimePatternService timePatternService,
                                   TreatmentService treatmentService) {
        this.prescriptionService = prescriptionService;
        this.eventService = eventService;
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
        eventService.create(prescriptionEntity);
    }

    @Override
    @Transactional
    public void updatePrescriptionAndEvents(PrescriptionDto prescriptionDto) {
        PrescriptionEntity prescriptionEntity = prescriptionService.prepareUpdate(prescriptionDto);
        setEntitiesAndSavePrescription(prescriptionDto, prescriptionEntity);
        log.info("Prescription updated");
        eventService.updateByPrescription(prescriptionEntity);
    }

    @Override
    @Transactional
    public void cancelPrescriptionAndEvents(String uuid) {
        PrescriptionEntity prescriptionEntity = prescriptionService.cancelByUuid(uuid);
        eventService.cancelByPrescription(prescriptionEntity);
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
