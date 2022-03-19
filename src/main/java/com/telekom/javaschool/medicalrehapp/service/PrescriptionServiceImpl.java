package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.constant.LogMessages;
import com.telekom.javaschool.medicalrehapp.dao.PatientRepository;
import com.telekom.javaschool.medicalrehapp.dao.PrescriptionRepository;
import com.telekom.javaschool.medicalrehapp.dao.TimePatternElementRepository;
import com.telekom.javaschool.medicalrehapp.dao.TimePatternRepository;
import com.telekom.javaschool.medicalrehapp.dao.TreatmentRepository;
import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionEntity;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionStatus;
import com.telekom.javaschool.medicalrehapp.entity.TimeBasis;
import com.telekom.javaschool.medicalrehapp.entity.TimePatternElementEntity;
import com.telekom.javaschool.medicalrehapp.entity.TimePatternEntity;
import com.telekom.javaschool.medicalrehapp.entity.TreatmentEntity;
import com.telekom.javaschool.medicalrehapp.mapper.PrescriptionMapper;
import com.telekom.javaschool.medicalrehapp.mapper.TimePatternMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
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
    private final TimePatternElementRepository timePatternElementRepository;
    private final TreatmentRepository treatmentRepository;

    @Autowired
    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository,
                                   PrescriptionMapper prescriptionMapper,
                                   PatientRepository patientRepository,
                                   TimePatternRepository timePatternRepository,
                                   TimePatternMapper timePatternMapper,
                                   TimePatternElementRepository timePatternElementRepository,
                                   TreatmentRepository treatmentRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.prescriptionMapper = prescriptionMapper;
        this.patientRepository = patientRepository;
        this.timePatternRepository = timePatternRepository;
        this.timePatternMapper = timePatternMapper;
        this.timePatternElementRepository = timePatternElementRepository;
        this.treatmentRepository = treatmentRepository;
    }

    @Override
    @Transactional
    public PrescriptionEntity create(PrescriptionDto prescriptionDto) {
        convertPeriodToDates(prescriptionDto);
        PrescriptionEntity prescriptionEntity = prescriptionMapper.dtoToEntity(prescriptionDto);
        prescriptionEntity.setPatient(getPatientEntityByInsuranceNumber(prescriptionDto
                .getPatient().getInsuranceNumber()));

        TimePatternEntity timePatternEntity = timePatternMapper.dtoToEntity(prescriptionDto.getTimePattern());
        timePatternRepository.save(timePatternEntity);
        setTimePatternElementsToEntity(timePatternEntity);

        prescriptionEntity.setTimePattern(timePatternEntity);
        prescriptionEntity.setTreatment(getTreatmentEntityByName(prescriptionDto.getTreatment().getName()));
        prescriptionEntity.setPrescriptionStatus(PrescriptionStatus.ACTIVE);
        PrescriptionEntity savedPrescriptionEntity = prescriptionRepository.save(prescriptionEntity);
        log.debug(savedPrescriptionEntity.toString());
        log.info("Prescription created");
        return savedPrescriptionEntity;
    }

    @Override
    @Transactional(readOnly = true)
    public PrescriptionDto findByUuid(String uuid) {
        return prescriptionMapper.entityToDto(getPrescriptionEntityByUuid(uuid));
    }

    @Override
    @Transactional
    public PrescriptionEntity update(PrescriptionDto prescriptionDto) {
        PrescriptionEntity prescriptionEntity = getPrescriptionEntityByUuid(prescriptionDto.getUuid().toString());
        prescriptionEntity.setPatient(getPatientEntityByInsuranceNumber(prescriptionDto
                .getPatient().getInsuranceNumber()));
        prescriptionEntity.setStartDateTime(LocalDateTime.now());
        prescriptionEntity.setEndDate(prescriptionDto.getEndDate());

        TimePatternEntity timePatternEntity = timePatternMapper.dtoToEntity(prescriptionDto.getTimePattern());
        timePatternRepository.save(timePatternEntity);
        setTimePatternElementsToEntity(timePatternEntity);

        prescriptionEntity.setTimePattern(timePatternEntity);
        prescriptionEntity.setTreatment(getTreatmentEntityByName(prescriptionDto.getTreatment().getName()));
        prescriptionEntity.setPrescriptionStatus(PrescriptionStatus.ACTIVE);
        log.info("Prescription updated");
        return prescriptionRepository.save(prescriptionEntity);
    }

    @Override
    @Transactional
    public List<PrescriptionDto> getAndCheckPrescriptionsByPatient(String insuranceNumber) {
        List<PrescriptionEntity> prescriptionEntities = prescriptionRepository
                .findPrescriptionsByInsuranceNumber(insuranceNumber);
        checkStatusIfCompleted(prescriptionEntities);
        return prescriptionMapper.entityListToDtoList(prescriptionEntities);
    }

    @Override
    @Transactional
    public PrescriptionEntity cancelByUuid(String uuid) {
        PrescriptionEntity prescriptionEntity = getPrescriptionEntityByUuid(uuid);
        prescriptionEntity.setPrescriptionStatus(PrescriptionStatus.CANCELED);
        return prescriptionRepository.save(prescriptionEntity);
    }

    @Override
    @Transactional
    public void cancelAllByPatient(PatientEntity patientEntity) {
        List<PrescriptionEntity> prescriptionEntities = prescriptionRepository
                .findPrescriptionsByInsuranceNumber(patientEntity.getInsuranceNumber());
        for (PrescriptionEntity entity : prescriptionEntities) {
            if (entity.getPrescriptionStatus() == PrescriptionStatus.ACTIVE) {
                entity.setPrescriptionStatus(PrescriptionStatus.CANCELED);
            }
        }
        prescriptionRepository.saveAll(prescriptionEntities);
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
        LocalDate endDate = startDateTime.plus(period).toLocalDate();
        prescriptionDto.setStartDateTime(startDateTime);
        prescriptionDto.setEndDate(endDate);
    }

    private void setTimePatternElementsToEntity(TimePatternEntity timePatternEntity) {
        if (timePatternEntity.getTimeBasis() == TimeBasis.WEEKLY) {
            List<TimePatternElementEntity> timePatternElementList = timePatternEntity.getTimePatternElement();
            timePatternElementList.removeIf(element -> element.getDayOfWeek() == null);
            for (TimePatternElementEntity element : timePatternElementList) {
                element.setTimePattern(timePatternEntity);
                timePatternElementRepository.save(element);
            }
        }
    }

    private PrescriptionEntity getPrescriptionEntityByUuid(String uuid) {
        return prescriptionRepository.findByUuid(UUID.fromString(uuid))
                .orElseThrow(() -> new EntityNotFoundException(String.format(LogMessages.PRESCRIPTION_NOT_FOUND, uuid)));
    }

    private PatientEntity getPatientEntityByInsuranceNumber(String insuranceNumber) {
        return patientRepository.findByInsuranceNumber(insuranceNumber)
                .orElseThrow(() -> new EntityNotFoundException(String
                        .format(LogMessages.PATIENT_NOT_FOUND, insuranceNumber)));
    }

    private TreatmentEntity getTreatmentEntityByName(String name) {
        return treatmentRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(String.format(LogMessages.TREATMENT_NOT_FOUND, name)));
    }

    private void checkStatusIfCompleted(List<PrescriptionEntity> prescriptionEntities) {
        for (PrescriptionEntity entity : prescriptionEntities) {
            if (entity.getPrescriptionStatus() == PrescriptionStatus.ACTIVE
                    && entity.getEndDate().isBefore(LocalDate.now())) {
                entity.setPrescriptionStatus(PrescriptionStatus.COMPLETED);
                prescriptionRepository.save(entity);
            }
        }
    }
}
