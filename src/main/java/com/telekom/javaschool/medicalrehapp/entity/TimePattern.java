package com.telekom.javaschool.medicalrehapp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "time_pattern")
public class TimePattern extends AbstractEntity {

    @Enumerated(value = EnumType.STRING)
    @Column(name = "time_basis")
    private TimeBasis timeBasis;

    @Column(name = "daily_frequency")
    private int dailyFrequency; // 1 - 4 times a day

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "timePattern", orphanRemoval = true)
    private List<TimePatternElement> timePatternElement;

    @OneToOne(mappedBy = "timePattern")
    private Prescription prescription;
}
