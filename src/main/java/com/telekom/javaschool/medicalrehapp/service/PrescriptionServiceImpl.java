package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.constant.LogMessages;
import com.telekom.javaschool.medicalrehapp.dao.PrescriptionRepository;
import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionEntity;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionStatus;
import com.telekom.javaschool.medicalrehapp.mapper.PrescriptionMapper;
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

    @Autowired
    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository,
                                   PrescriptionMapper prescriptionMapper) {
        this.prescriptionRepository = prescriptionRepository;
        this.prescriptionMapper = prescriptionMapper;
    }

    @Override
    public PrescriptionEntity prepareCreate(PrescriptionDto prescriptionDto) {
        convertPeriodToDates(prescriptionDto);
        return prescriptionMapper.dtoToEntity(prescriptionDto);
    }

    @Override
    public PrescriptionEntity prepareUpdate(PrescriptionDto prescriptionDto) {
        PrescriptionEntity prescriptionEntity = getPrescriptionEntityByUuid(prescriptionDto.getUuid().toString());
        prescriptionEntity.setStartDateTime(LocalDateTime.now());
        prescriptionEntity.setEndDate(prescriptionDto.getEndDate());
        prescriptionEntity.setDosage(prescriptionDto.getDosage());
        prescriptionEntity.setDosageUnit(prescriptionDto.getDosageUnit());
        return prescriptionEntity;
    }

    @Override
    @Transactional
    public PrescriptionEntity save(PrescriptionEntity prescriptionEntity) {
        prescriptionEntity.setPrescriptionStatus(PrescriptionStatus.ACTIVE);
        return prescriptionRepository.save(prescriptionEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public PrescriptionDto findByUuid(String uuid) {
        return prescriptionMapper.entityToDto(getPrescriptionEntityByUuid(uuid));
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

    private PrescriptionEntity getPrescriptionEntityByUuid(String uuid) {
        return prescriptionRepository.findByUuid(UUID.fromString(uuid))
                .orElseThrow(() -> new EntityNotFoundException(String.format(LogMessages.PRESCRIPTION_NOT_FOUND, uuid)));
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
