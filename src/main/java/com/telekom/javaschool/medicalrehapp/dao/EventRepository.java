package com.telekom.javaschool.medicalrehapp.dao;

import com.telekom.javaschool.medicalrehapp.entity.EventEntity;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    List<EventEntity> findAllByOrderByDateTimeAsc();

    List<EventEntity> findAllByPrescription(PrescriptionEntity prescription);

    List<EventEntity> findAllByPatient(PatientEntity patientEntity);

    Optional<EventEntity> findByUuid(UUID uuid);
}