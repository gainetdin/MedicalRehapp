package com.telekom.javaschool.medicalrehapp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.DayOfWeek;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "time_pattern_element")
public class TimePatternElementEntity extends AbstractEntity {

    @Enumerated(value = EnumType.STRING)
    @Column(name = "day_of_week")
    private DayOfWeek dayOfWeek;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "time_pattern_id")
    private TimePatternEntity timePattern;

}
