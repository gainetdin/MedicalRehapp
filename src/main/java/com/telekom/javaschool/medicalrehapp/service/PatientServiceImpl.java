package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dao.DoctorRepository;
import com.telekom.javaschool.medicalrehapp.dao.PatientRepository;
import com.telekom.javaschool.medicalrehapp.dto.PatientDto;
import com.telekom.javaschool.medicalrehapp.entity.Patient;
import com.telekom.javaschool.medicalrehapp.entity.PatientStatus;
import com.telekom.javaschool.medicalrehapp.mapper.DoctorMapper;
import com.telekom.javaschool.medicalrehapp.mapper.PatientMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;

@Slf4j
@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final DoctorMapper doctorMapper;
    private final DoctorRepository doctorRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository,
                              PatientMapper patientMapper,
                              DoctorMapper doctorMapper, DoctorRepository doctorRepository) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
        this.doctorMapper = doctorMapper;
        this.doctorRepository = doctorRepository;
    }

    @Override
    @Transactional
    public void create(PatientDto patientDto) {
        if (patientRepository.findByInsuranceNumber(patientDto.getInsuranceNumber()) != null) {
            throw new EntityExistsException("Patient with this insurance number already exists");
        }
        patientDto.setPatientStatus(PatientStatus.BEING_TREATED);
        Patient patientToDb = patientMapper.dtoToEntity(patientDto);
        patientToDb.setDoctor(doctorRepository.findByName(patientDto.getDoctor().getName()));
        log.debug(patientDto.toString());
        patientRepository.save(patientToDb);
    }

    @Override
    @Transactional
    public void update(PatientDto patientDto) {
        Patient patientFromDb = patientRepository.findByInsuranceNumber(patientDto.getInsuranceNumber());
        patientFromDb.setDiagnosis(patientDto.getDiagnosis());
        patientFromDb.setPatientStatus(patientDto.getPatientStatus());
        patientFromDb.setDoctor(doctorMapper.dtoToEntity(patientDto.getDoctor()));
        patientRepository.save(patientFromDb);
    }

    @Override
    public PatientDto findByInsuranceNumber(String insuranceNumber) {
        return patientMapper.entityToDto(patientRepository.findByInsuranceNumber(insuranceNumber));
    }

    @Override
    public void discharge(PatientDto patientDto) {
        Patient patientFromDb = patientRepository.findByInsuranceNumber(patientDto.getInsuranceNumber());
        patientFromDb.setPatientStatus(PatientStatus.DISCHARGED);
        patientRepository.save(patientFromDb);
    }
}
