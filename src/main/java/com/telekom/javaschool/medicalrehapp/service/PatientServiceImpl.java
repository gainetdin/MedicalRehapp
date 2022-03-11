package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dao.DoctorRepository;
import com.telekom.javaschool.medicalrehapp.dao.PatientRepository;
import com.telekom.javaschool.medicalrehapp.dto.PatientDto;
import com.telekom.javaschool.medicalrehapp.entity.DoctorEntity;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import com.telekom.javaschool.medicalrehapp.entity.PatientStatus;
import com.telekom.javaschool.medicalrehapp.mapper.PatientMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final DoctorRepository doctorRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository,
                              PatientMapper patientMapper,
                              DoctorRepository doctorRepository) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
        this.doctorRepository = doctorRepository;
    }

    @Override
    @Transactional
    public void create(PatientDto patientDto) {
        if (patientRepository.findByInsuranceNumber(patientDto.getInsuranceNumber()).isPresent()) {
            throw new EntityExistsException("Patient with this insurance number already exists");
        }
        patientDto.setPatientStatus(PatientStatus.BEING_TREATED);
        PatientEntity patientEntity = patientMapper.dtoToEntity(patientDto);
        patientEntity.setDoctor(getDoctorEntity(patientDto));
        log.debug(patientDto.toString());
        patientRepository.save(patientEntity);
    }

    @Override
    @Transactional
    public void update(PatientDto patientDto) {
        PatientEntity patientEntity = getPatientEntityByInsuranceNumber(patientDto.getInsuranceNumber());
        patientEntity.setDiagnosis(patientDto.getDiagnosis());
        patientEntity.setPatientStatus(patientDto.getPatientStatus());
        patientEntity.setDoctor(getDoctorEntity(patientDto));
        patientRepository.save(patientEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientDto> findAll() {
        return patientMapper.entityListToDtoList(patientRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public PatientDto findByInsuranceNumber(String insuranceNumber) {
        return patientMapper.entityToDto(getPatientEntityByInsuranceNumber(insuranceNumber));
    }

    @Override
    @Transactional
    public void discharge(PatientDto patientDto) {
        PatientEntity patientEntity = getPatientEntityByInsuranceNumber(patientDto.getInsuranceNumber());
        patientEntity.setPatientStatus(PatientStatus.DISCHARGED);
        patientRepository.save(patientEntity);
    }

    private PatientEntity getPatientEntityByInsuranceNumber(String insuranceNumber) {
        return patientRepository.findByInsuranceNumber(insuranceNumber)
                .orElseThrow(() -> new EntityNotFoundException("Patient with this insurance number doesn't exist"));
    }

    private DoctorEntity getDoctorEntity(PatientDto patientDto) {
        return doctorRepository.findByName(patientDto.getDoctor().getName())
                .orElseThrow(() -> new EntityNotFoundException("Doctor with this name doesn't exist"));
    }
}
