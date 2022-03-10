package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dao.PatientRepository;
import com.telekom.javaschool.medicalrehapp.dao.PrescriptionRepository;
import com.telekom.javaschool.medicalrehapp.dao.TimePatternRepository;
import com.telekom.javaschool.medicalrehapp.dao.TreatmentRepository;
import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.entity.Patient;
import com.telekom.javaschool.medicalrehapp.entity.Prescription;
import com.telekom.javaschool.medicalrehapp.entity.TimePattern;
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
import java.util.stream.Collectors;

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
    public void create(PrescriptionDto prescriptionDto) {
        convertPeriodToDates(prescriptionDto);
        Prescription prescriptionFromDb = prescriptionMapper.dtoToEntity(prescriptionDto);
        prescriptionFromDb.setPatient(patientRepository
                .findByInsuranceNumber(prescriptionDto.getPatient().getInsuranceNumber()));
        TimePattern timePattern = timePatternMapper.dtoToEntity(prescriptionDto.getTimePattern());
        timePatternRepository.save(timePattern);
        prescriptionFromDb.setTimePattern(timePattern);
        prescriptionFromDb.setTreatment(treatmentRepository
                .findById(prescriptionDto.getTreatment().getId()).get());
        prescriptionRepository.save(prescriptionFromDb);

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
}
