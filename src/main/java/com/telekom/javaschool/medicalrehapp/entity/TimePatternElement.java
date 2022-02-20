package com.telekom.javaschool.medicalrehapp.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.DayOfWeek;

@Entity
public class TimePatternElement extends AbstractEntity {

    private DayOfWeek dayOfWeek;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "time_pattern_id")
    private TimePattern timePattern;

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public TimePattern getTimePattern() {
        return timePattern;
    }

    public void setTimePattern(TimePattern timePattern) {
        this.timePattern = timePattern;
    }

    public TimePatternElement() {
    }
}
