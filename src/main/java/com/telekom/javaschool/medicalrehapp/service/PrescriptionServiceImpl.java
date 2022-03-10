package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dao.PatientRepository;
import com.telekom.javaschool.medicalrehapp.dao.PrescriptionRepository;
import com.telekom.javaschool.medicalrehapp.dao.TimePatternRepository;
import com.telekom.javaschool.medicalrehapp.dao.TreatmentRepository;
import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionEntity;
import com.telekom.javaschool.medicalrehapp.entity.TimePatternEntity;
import com.telekom.javaschool.medicalrehapp.entity.TreatmentEntity;
import com.telekom.javaschool.medicalrehapp.mapper.PrescriptionMapper;
import com.telekom.javaschool.medicalrehapp.mapper.TimePatternMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionMapper prescriptionMapper;
    private final PatientRepository patientRepository;
    private final TimePatternRepository timePatternRepository;
    private final TimePatternMapper timePatternMapper;
    private final TreatmentRepository treatmentRepository;

    @Autowired
    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository,
                                   PrescriptionMapper prescriptionMapper,
                                   PatientRepository patientRepository,
                                   TimePatternRepository timePatternRepository, TimePatternMapper timePatternMapper, TreatmentRepository treatmentRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.prescriptionMapper = prescriptionMapper;
        this.patientRepository = patientRepository;
        this.timePatternRepository = timePatternRepository;
        this.timePatternMapper = timePatternMapper;
        this.treatmentRepository = treatmentRepository;
    }

    @Override
    @Transactional
    public void save(PrescriptionDto prescriptionDto) {
        convertPeriodToDates(prescriptionDto);
        PrescriptionEntity prescriptionEntity = prescriptionMapper.dtoToEntity(prescriptionDto);
        prescriptionEntity.setPatient(getPatientEntityByInsuranceNumber(prescriptionDto
                .getPatient().getInsuranceNumber()));
        TimePatternEntity timePatternEntity = timePatternMapper.dtoToEntity(prescriptionDto.getTimePattern());
        timePatternRepository.save(timePatternEntity);
        prescriptionEntity.setTimePattern(timePatternEntity);
        prescriptionEntity.setTreatment(getTreatmentEntityById(prescriptionDto.getTreatment().getId()));
        prescriptionRepository.save(prescriptionEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public PrescriptionDto findByUuid(String uuid) {
        return prescriptionMapper.entityToDto(prescriptionRepository.findByUuid(UUID.fromString(uuid))
                .orElseThrow(() -> new EntityNotFoundException("Prescription with this UUID doesn't exist")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionDto> findPrescriptionsByPatient(String insuranceNumber) {
        PatientEntity patientEntity = getPatientEntityByInsuranceNumber(insuranceNumber);
        return prescriptionMapper.entityListToDtoList(prescriptionRepository.findPrescriptionsByPatient(patientEntity));
    }

    private void convertPeriodToDates(PrescriptionDto prescriptionDto) {
        ChronoUnit periodUnit = prescriptionDto.getPeriodUnit();
        int periodValue = prescriptionDto.getPeriodValue();
        LocalDateTime startDateTime = LocalDateTime.now();
        Period period;
        switch (periodUnit) {
            case DAYS:
                period = Period.ofDays(periodValue);
                break;
            case WEEKS:
                period = Period.ofWeeks(periodValue);
                break;
            case MONTHS:
                period = Period.ofMonths(periodValue);
                break;
            default:
                throw new IllegalArgumentException("Wrong period unit");
        }
        LocalDateTime endDateTime = startDateTime.plus(period);
        prescriptionDto.setStartDateTime(startDateTime);
        prescriptionDto.setEndDateTime(endDateTime);
    }

    private PatientEntity getPatientEntityByInsuranceNumber(String insuranceNumber) {
        return patientRepository.findByInsuranceNumber(insuranceNumber)
                .orElseThrow(() -> new EntityNotFoundException("Patient with this insurance number doesn't exist"));
    }

    private TreatmentEntity getTreatmentEntityById(long id) {
        return treatmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("This treatment doesn't exist"));
    }
}
