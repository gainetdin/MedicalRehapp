package com.telekom.javaschool.medicalrehapp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "treatment")
public class Treatment extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Enumerated
    @Column(name = "type")
    private TreatmentType type;

}
