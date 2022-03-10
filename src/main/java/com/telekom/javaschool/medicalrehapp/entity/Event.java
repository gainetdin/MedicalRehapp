package com.telekom.javaschool.medicalrehapp.entity;

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
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "event")
public class Event extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "event_status")
    private EventStatus eventStatus;

    @OneToOne
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;

    @Column(name = "cancel_reason")
    private String cancelReason;

}
