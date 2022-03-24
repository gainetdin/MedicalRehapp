package com.telekom.javaschool.medicalrehapp.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "event")
public class EventEntity extends AbstractEntity {

    @Column(name = "uuid", nullable = false, columnDefinition = "VARCHAR(255)")
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "event_status", nullable = false)
    private EventStatus eventStatus;

    @OneToOne
    @JoinColumn(name = "treatment_id")
    private TreatmentEntity treatment;

    @Column(name = "cancel_reason")
    private String cancelReason;

    @ManyToOne
    @JoinColumn(name = "prescription_id")
    private PrescriptionEntity prescription;

    @PrePersist
    private void generateUuid() {
        uuid = UUID.randomUUID();
    }
}
