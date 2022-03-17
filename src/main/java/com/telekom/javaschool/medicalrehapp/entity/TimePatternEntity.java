package com.telekom.javaschool.medicalrehapp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
@ToString
@Entity
@Table(name = "time_pattern")
public class TimePatternEntity extends AbstractEntity {

    @Enumerated(value = EnumType.STRING)
    @Column(name = "time_basis")
    private TimeBasis timeBasis;

    @Column(name = "daily_frequency")
    private int dailyFrequency;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "timePattern", orphanRemoval = true)
    @ToString.Exclude
    private List<TimePatternElementEntity> timePatternElement;

    @OneToOne(mappedBy = "timePattern")
    private PrescriptionEntity prescription;
}
