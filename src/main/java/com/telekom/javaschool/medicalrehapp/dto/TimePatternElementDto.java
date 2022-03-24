package com.telekom.javaschool.medicalrehapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimePatternElementDto extends AbstractDto implements Serializable {
    private DayOfWeek dayOfWeek;

    public static List<TimePatternElementDto> getWrappedDaysOfWeek() {
        List<TimePatternElementDto> daysOfWeek = new ArrayList<>();
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            daysOfWeek.add(new TimePatternElementDto(dayOfWeek));
        }
        return daysOfWeek;
    }
}
