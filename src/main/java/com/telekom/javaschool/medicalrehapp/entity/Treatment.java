package com.telekom.javaschool.medicalrehapp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "treatment")
public class Treatment extends AbstractEntity {

    @Column(name = "name", unique = true)
    private String name;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    private TreatmentType type;

}
