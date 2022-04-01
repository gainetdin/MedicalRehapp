package com.telekom.javaschool.medicalrehapp.dao;

import com.telekom.javaschool.medicalrehapp.entity.EventEntity;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    Page<EventEntity> findAllByOrderByDateTimeAsc(Pageable pageRequest);

    List<EventEntity> findAllByPrescription(PrescriptionEntity prescriptionEntity);

    List<EventEntity> findAllByPatient(PatientEntity patientEntity);

    Optional<EventEntity> findByUuid(UUID uuid);

    List<EventEntity> findAllByDateTimeBetweenOrderByDateTimeAsc(LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Query(value = "SELECT * FROM mrschema.event e INNER JOIN mrschema.patient p ON e.patient_id = p.id " +
            "WHERE p.insurance_number = :insurance_number", nativeQuery = true)
    List<EventEntity> findAllByInsuranceNumber(@Param("insurance_number") String insuranceNumber);
}