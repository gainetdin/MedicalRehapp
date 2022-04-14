package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dao.PatientRepository;
import com.telekom.javaschool.medicalrehapp.dto.PatientDto;
import com.telekom.javaschool.medicalrehapp.entity.DoctorEntity;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import com.telekom.javaschool.medicalrehapp.entity.PatientStatus;
import com.telekom.javaschool.medicalrehapp.mapper.PatientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @InjectMocks
    private PatientServiceImpl patientService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @Captor
    private ArgumentCaptor<PatientDto> patientDtoCaptor;

    @Captor
    private ArgumentCaptor<PatientEntity> patientEntityCaptor;

    private PatientEntity patientEntity;

    @BeforeEach
    void initData() {
        patientEntity = new PatientEntity();
    }

    @Test
    void shouldSetStatusOnCreate() {
        Mockito.when(patientMapper.dtoToEntity(Mockito.any()))
                .thenReturn(patientEntity);
        patientService.create(new PatientDto(), new DoctorEntity());
        Mockito.verify(patientMapper).dtoToEntity(patientDtoCaptor.capture());
        PatientDto patientDtoActual = patientDtoCaptor.getValue();

        assertEquals(PatientStatus.BEING_TREATED, patientDtoActual.getPatientStatus());
    }

    @Test
    void shouldChangeStatusOnDischargingPatient() {
        Mockito.when(patientRepository.findByInsuranceNumber(Mockito.any()))
                .thenReturn(Optional.of(patientEntity));
        patientService.discharge("");
        Mockito.verify(patientRepository).save(patientEntityCaptor.capture());
        PatientEntity patientEntityActual = patientEntityCaptor.getValue();

        assertEquals(PatientStatus.DISCHARGED, patientEntityActual.getPatientStatus());
    }

    @Test
    void shouldChangeStatusOnTakingBackPatient() {
        Mockito.when(patientRepository.findByInsuranceNumber(Mockito.any()))
                .thenReturn(Optional.of(patientEntity));
        patientService.takeBack("");
        Mockito.verify(patientRepository).save(patientEntityCaptor.capture());
        PatientEntity patientEntityActual = patientEntityCaptor.getValue();

        assertEquals(PatientStatus.BEING_TREATED, patientEntityActual.getPatientStatus());
    }
}