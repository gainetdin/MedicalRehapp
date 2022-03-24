package com.telekom.javaschool.medicalrehapp.dao;

import com.telekom.javaschool.medicalrehapp.entity.PrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PrescriptionRepository extends JpaRepository<PrescriptionEntity, Long> {

    @Query(value = "SELECT * FROM mrschema.prescription pr INNER JOIN mrschema.patient p ON pr.patient_id = p.id " +
            "WHERE p.insurance_number = :insurance_number", nativeQuery = true)
    List<PrescriptionEntity> findPrescriptionsByInsuranceNumber(@Param("insurance_number") String insuranceNumber);

    Optional<PrescriptionEntity> findByUuid(UUID uuid);
}