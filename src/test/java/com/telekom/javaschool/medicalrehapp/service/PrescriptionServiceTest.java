package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dao.PrescriptionRepository;
import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionEntity;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionStatus;
import com.telekom.javaschool.medicalrehapp.mapper.PrescriptionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PrescriptionServiceTest {

    @InjectMocks
    private PrescriptionServiceImpl prescriptionService;

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private PrescriptionMapper prescriptionMapper;

    @Captor
    private ArgumentCaptor<PrescriptionDto> prescriptionDtoCaptor;

    @Captor
    private ArgumentCaptor<List<PrescriptionEntity>> prescriptionEntitiesCaptor;

    @Captor
    private ArgumentCaptor<PrescriptionEntity> prescriptionEntityCaptor;

    private List<PrescriptionEntity> prescriptionEntities;

    @BeforeEach
    void initData() {
        PrescriptionEntity prescriptionEntity = new PrescriptionEntity();
        prescriptionEntity.setPrescriptionStatus(PrescriptionStatus.ACTIVE);
        prescriptionEntity.setEndDate(LocalDate.of(2022, Month.MARCH, 1));
        prescriptionEntities = new ArrayList<>();
        prescriptionEntities.add(prescriptionEntity);
    }

    @Test
    void shouldConvertPeriodToDatesOnCreate() {
        PrescriptionDto prescriptionDto = new PrescriptionDto();
        prescriptionDto.setPeriodValue(2);
        prescriptionDto.setPeriodUnit(ChronoUnit.WEEKS);
        Period periodExpected = Period.ofWeeks(2);

        prescriptionService.prepareCreate(prescriptionDto);
        Mockito.verify(prescriptionMapper).dtoToEntity(prescriptionDtoCaptor.capture());
        PrescriptionDto prescriptionDtoActual = prescriptionDtoCaptor.getValue();
        LocalDate startDate = prescriptionDtoActual.getStartDateTime().toLocalDate();
        LocalDate endDate = prescriptionDtoActual.getEndDate();
        Period periodActual = Period.between(startDate, endDate);

        assertEquals(periodExpected, periodActual);
    }

    @Test
    void shouldSetNewDatesOnUpdate() {
        PrescriptionDto prescriptionDto = new PrescriptionDto();
        LocalDate endDate = LocalDate.of(3022, Month.MARCH, 31);
        prescriptionDto.setEndDate(endDate);
        prescriptionDto.setUuid(UUID.randomUUID());
        Period periodExpected = Period.between(LocalDate.now(), endDate);

        Mockito.when(prescriptionRepository.findByUuid(Mockito.any()))
                .thenReturn(Optional.of(new PrescriptionEntity()));
        PrescriptionEntity prescriptionEntity = prescriptionService.prepareUpdate(prescriptionDto);
        Period periodActual = Period.between(prescriptionEntity.getStartDateTime().toLocalDate(),
                prescriptionEntity.getEndDate());

        assertEquals(periodExpected, periodActual);
    }

    @Test
    void shouldChangeStatusIfCompletedByTime() {
        String insuranceNumber = "insurance number";
        Mockito.when(prescriptionRepository.findPrescriptionsByInsuranceNumber(insuranceNumber))
                .thenReturn(prescriptionEntities);
        prescriptionService.getAndCheckPrescriptionsByPatient(insuranceNumber);
        Mockito.verify(prescriptionMapper).entityListToDtoList(prescriptionEntitiesCaptor.capture());
        PrescriptionEntity prescriptionActual = prescriptionEntitiesCaptor.getValue().get(0);

        assertEquals(PrescriptionStatus.COMPLETED, prescriptionActual.getPrescriptionStatus());
    }

    @Test
    void shouldCancelPrescriptionsByDischargingPatient() {
        Mockito.when(prescriptionRepository.findPrescriptionsByInsuranceNumber(Mockito.any()))
                .thenReturn(prescriptionEntities);
        prescriptionService.cancelAllByPatient(new PatientEntity());
        Mockito.verify(prescriptionRepository).saveAll(prescriptionEntitiesCaptor.capture());
        PrescriptionEntity prescriptionActual = prescriptionEntitiesCaptor.getValue().get(0);

        assertEquals(PrescriptionStatus.CANCELED, prescriptionActual.getPrescriptionStatus());
    }

    @Test
    void shouldCancelPrescription() {
        Mockito.when(prescriptionRepository.findByUuid(Mockito.any()))
                .thenReturn(Optional.of(prescriptionEntities.get(0)));
        prescriptionService.cancelByUuid(UUID.randomUUID().toString());
        Mockito.verify(prescriptionRepository).save(prescriptionEntityCaptor.capture());
        PrescriptionEntity prescriptionActual = prescriptionEntityCaptor.getValue();

        assertEquals(PrescriptionStatus.CANCELED, prescriptionActual.getPrescriptionStatus());
    }

    @Test
    void shouldThrowExceptionOnIllegalPeriod() {
        PrescriptionDto prescriptionDto = new PrescriptionDto();
        prescriptionDto.setPeriodUnit(ChronoUnit.YEARS);

        assertThrows(IllegalArgumentException.class, () -> prescriptionService.prepareCreate(prescriptionDto));
    }
}