package com.telekom.javaschool.medicalrehapp.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class TimePattern extends AbstractEntity {

    private TimeBasis timeBasis;
    private int dailyFrequency; // 1 - 4 times a day
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "timePattern", orphanRemoval = true)
    private List<TimePatternElement> timePatternElement;

    public TimeBasis getTimeBasis() {
        return timeBasis;
    }

    public void setTimeBasis(TimeBasis timeBasis) {
        this.timeBasis = timeBasis;
    }

    public int getDailyFrequency() {
        return dailyFrequency;
    }

    public void setDailyFrequency(int dailyFrequency) {
        this.dailyFrequency = dailyFrequency;
    }

    public List<TimePatternElement> getTimePatternElement() {
        return timePatternElement;
    }

    public void setTimePatternElement(List<TimePatternElement> timePatternElement) {
        this.timePatternElement = timePatternElement;
    }

    public TimePattern() {
    }
}
