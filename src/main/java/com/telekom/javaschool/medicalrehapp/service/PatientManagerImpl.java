package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientManagerImpl implements PatientManager {

    private final PatientService patientService;
    private final PrescriptionService prescriptionService;
    private final EventService eventService;

    @Autowired
    public PatientManagerImpl(PatientService patientService,
                              PrescriptionService prescriptionService,
                              EventService eventService) {
        this.patientService = patientService;
        this.prescriptionService = prescriptionService;
        this.eventService = eventService;
    }

    @Override
    public void dischargePatientAndCancelEverything(String insuranceNumber) {
        PatientEntity patientEntity = patientService.discharge(insuranceNumber);
        prescriptionService.cancelAllByPatient(patientEntity);
        eventService.cancelByPatient(patientEntity);
    }
}
