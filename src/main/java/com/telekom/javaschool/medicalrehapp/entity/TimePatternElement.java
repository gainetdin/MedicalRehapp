package com.telekom.javaschool.medicalrehapp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Entity
@Table(name = "time_pattern_element")
public class TimePatternElement extends AbstractEntity {

    @Enumerated(value = EnumType.STRING)
    @Column(name = "day_of_week")
    private DayOfWeek dayOfWeek;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "time_pattern_id")
    private TimePattern timePattern;

}
