package com.telekom.javaschool.medicalrehapp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "prescription")
public class PrescriptionEntity extends AbstractEntity {

    @Column(name = "uuid")
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @ManyToOne
    @JoinColumn(name = "treatment_id")
    private TreatmentEntity treatment;

    @OneToOne
    @JoinColumn(name = "time_pattern_id")
    private TimePatternEntity timePattern;

    @Column(name = "start_date_time")
    private LocalDateTime startDateTime;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "dosage")
    private double dosage;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "dosage_unit")
    private DosageUnit dosageUnit;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "prescription_status")
    private PrescriptionStatus prescriptionStatus;

    @PrePersist
    private void generateUuid() {
        uuid = UUID.randomUUID();
    }
}
