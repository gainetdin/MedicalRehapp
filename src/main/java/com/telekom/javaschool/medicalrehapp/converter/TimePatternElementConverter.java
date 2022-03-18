package com.telekom.javaschool.medicalrehapp.converter;

import com.telekom.javaschool.medicalrehapp.dto.TimePatternElementDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class TimePatternElementConverter implements Converter<String, TimePatternElementDto> {

    private static final List<TimePatternElementDto> WRAPPED_DAYS_OF_WEEK = TimePatternElementDto.getWrappedDaysOfWeek();

    @Override
    public TimePatternElementDto convert(String wrappedDayOfWeekName) {
        for (TimePatternElementDto day : WRAPPED_DAYS_OF_WEEK) {
            if (day.toString().equals(wrappedDayOfWeekName)) {
                return day;
            }
        }
        return null;
    }
}
