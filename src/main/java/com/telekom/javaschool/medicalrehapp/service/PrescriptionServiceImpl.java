package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dao.PatientRepository;
import com.telekom.javaschool.medicalrehapp.dao.PrescriptionRepository;
import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.entity.Patient;
import com.telekom.javaschool.medicalrehapp.mapper.PrescriptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionMapper prescriptionMapper;
    private final PatientRepository patientRepository;

    @Autowired
    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository,
                                   PrescriptionMapper prescriptionMapper,
                                   PatientRepository patientRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.prescriptionMapper = prescriptionMapper;
        this.patientRepository = patientRepository;
    }

    @Override
    @Transactional
    public void create(PrescriptionDto prescriptionDto) {
//        if (prescriptionRepository.findById(prescriptionDto.getId()).isPresent()) {
//            throw new EntityExistsException("This prescription already exists");
//        }

    }

    @Override
    @Transactional
    public void update(PrescriptionDto prescriptionDto) {

    }

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionDto> findAll() {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public PrescriptionDto findById(String id) {
        return prescriptionMapper.entityToDto(prescriptionRepository
                .findById(Long.valueOf(id))
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionDto> findPrescriptionsByPatient(String insuranceNumber) {
        Patient patientFromDb = patientRepository.findByInsuranceNumber(insuranceNumber);
        return prescriptionRepository.findPrescriptionsByPatient(patientFromDb)
                .stream()
                .map(prescriptionMapper::entityToDto)
                .collect(Collectors.toList());
    }
}
