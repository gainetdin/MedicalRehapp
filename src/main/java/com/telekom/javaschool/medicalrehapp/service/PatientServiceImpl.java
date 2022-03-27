package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.constant.LogMessages;
import com.telekom.javaschool.medicalrehapp.dao.DoctorRepository;
import com.telekom.javaschool.medicalrehapp.dao.PatientRepository;
import com.telekom.javaschool.medicalrehapp.dto.PatientDto;
import com.telekom.javaschool.medicalrehapp.entity.DoctorEntity;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import com.telekom.javaschool.medicalrehapp.entity.PatientStatus;
import com.telekom.javaschool.medicalrehapp.mapper.PatientMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
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
        String insuranceNumber = patientDto.getInsuranceNumber();
        if (patientRepository.findByInsuranceNumber(insuranceNumber).isPresent()) {
            throw new EntityExistsException(String.format(LogMessages.PATIENT_EXISTS, insuranceNumber));
        }
        patientDto.setPatientStatus(PatientStatus.BEING_TREATED);
        PatientEntity patientEntity = patientMapper.dtoToEntity(patientDto);
        String doctorLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        patientEntity.setDoctor(getDoctorEntity(doctorLogin));
        log.debug(patientDto.toString());
        patientRepository.save(patientEntity);
    }

    @Override
    @Transactional
    public void update(PatientDto patientDto) {
        PatientEntity patientEntity = getPatientEntityByInsuranceNumber(patientDto.getInsuranceNumber());
        patientEntity.setDiagnosis(patientDto.getDiagnosis());
        String doctorLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        patientEntity.setDoctor(getDoctorEntity(doctorLogin));
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
    public PatientEntity discharge(String insuranceNumber) {
        PatientEntity patientEntity = getPatientEntityByInsuranceNumber(insuranceNumber);
        patientEntity.setPatientStatus(PatientStatus.DISCHARGED);
        return patientRepository.save(patientEntity);
    }

    @Override
    @Transactional
    public PatientEntity takeBack(String insuranceNumber) {
        PatientEntity patientEntity = getPatientEntityByInsuranceNumber(insuranceNumber);
        patientEntity.setPatientStatus(PatientStatus.BEING_TREATED);
        return patientRepository.save(patientEntity);
    }

    private PatientEntity getPatientEntityByInsuranceNumber(String insuranceNumber) {
        return patientRepository.findByInsuranceNumber(insuranceNumber)
                .orElseThrow(() -> new EntityNotFoundException(String.format(LogMessages.PATIENT_NOT_FOUND, insuranceNumber)));
    }

    private DoctorEntity getDoctorEntity(String login) {
        return doctorRepository.findByUserLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(String.format(LogMessages.DOCTOR_NOT_FOUND, login)));
    }
}
