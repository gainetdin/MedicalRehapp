package com.telekom.javaschool.medicalrehapp.dao;

import com.telekom.javaschool.medicalrehapp.entity.EventEntity;
import com.telekom.javaschool.medicalrehapp.entity.EventStatus;
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

    List<EventEntity> findAllByPrescription(PrescriptionEntity prescriptionEntity);

    List<EventEntity> findAllByPatient(PatientEntity patientEntity);

    Optional<EventEntity> findByUuid(UUID uuid);

    Page<EventEntity> findAllByDateTimeBetweenAndEventStatusIsInOrderByDateTimeAsc(
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            List<EventStatus> statuses,
            Pageable pageable
    );

    @Query(value = "SELECT * FROM mrschema.event e INNER JOIN mrschema.patient p ON e.patient_id = p.id " +
            "WHERE p.name ILIKE '%' || :name || '%'" +
            "  AND e.date_time BETWEEN :start_date_time AND :end_date_time" +
            "  AND e.event_status IN :#{#statuses.![name()]} " +
            "ORDER BY e.date_time ASC", nativeQuery = true)
    Page<EventEntity> findAllByDateTimeBetweenAndEventStatusIsInAndPatientNameLikeOrderByDateTimeAsc(
            @Param("name") String name,
            @Param("start_date_time") LocalDateTime startDateTime,
            @Param("end_date_time") LocalDateTime endDateTime,
            @Param("statuses") List<EventStatus> statuses,
            Pageable pageable
    );

    List<EventEntity> findAllByDateTimeBetweenAndEventStatus(
            LocalDateTime startDateTime, LocalDateTime endDateTime, EventStatus status);
}