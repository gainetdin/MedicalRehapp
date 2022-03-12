package com.telekom.javaschool.medicalrehapp.converter;

import com.telekom.javaschool.medicalrehapp.dto.TimePatternElementDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TimePatternElementConverter implements Converter<String, TimePatternElementDto> {

    private final List<TimePatternElementDto> elementsList = new ArrayList<>();

    {
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            elementsList.add(new TimePatternElementDto(dayOfWeek));
        }
    }

    @Override
    public TimePatternElementDto convert(String wrappedDayOfWeekName) {
        for (TimePatternElementDto day : elementsList) {
            if (day.toString().equals(wrappedDayOfWeekName)) {
                return day;
            }
        }
        return null;
    }
}
