package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.PatientDto;
import com.telekom.javaschool.medicalrehapp.entity.DoctorEntity;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientManagerImpl implements PatientManager {

    private final PatientService patientService;
    private final PrescriptionService prescriptionService;
    private final EventManager eventManager;
    private final DoctorService doctorService;

    @Autowired
    public PatientManagerImpl(PatientService patientService,
                              PrescriptionService prescriptionService,
                              EventManager eventManager,
                              DoctorService doctorService) {
        this.patientService = patientService;
        this.prescriptionService = prescriptionService;
        this.eventManager = eventManager;
        this.doctorService = doctorService;
    }

    @Override
    @Transactional
    public void dischargePatientAndCancelEverything(String insuranceNumber) {
        PatientEntity patientEntity = patientService.discharge(insuranceNumber);
        prescriptionService.cancelAllByPatient(patientEntity);
        eventManager.cancelEventsByPatient(patientEntity);
    }

    @Override
    @Transactional
    public void createPatient(PatientDto patientDto) {
        DoctorEntity doctorEntity = doctorService.getDoctorFromSecurityContext();
        patientService.create(patientDto, doctorEntity);
    }

    @Override
    @Transactional
    public void updatePatient(PatientDto patientDto) {
        DoctorEntity doctorEntity = doctorService.getDoctorFromSecurityContext();
        patientService.update(patientDto, doctorEntity);
    }
}
