package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.EventDto;
import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.dto.TimePatternDto;
import com.telekom.javaschool.medicalrehapp.dto.TimePatternElementDto;
import com.telekom.javaschool.medicalrehapp.entity.TimeBasis;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private PrescriptionService prescriptionService;
    private static PrescriptionDto prescriptionDto;
    @InjectMocks
    private EventServiceImpl eventService;

    @BeforeAll
    static void initPrescription() {
        List<TimePatternElementDto> daysList = new ArrayList<>();
        daysList.add(new TimePatternElementDto(DayOfWeek.MONDAY));
        daysList.add(new TimePatternElementDto(DayOfWeek.TUESDAY));

        prescriptionDto = PrescriptionDto.builder()
                .startDateTime(LocalDateTime.of(2022, Month.MARCH, 14, 15, 0))
                .endDate(LocalDate.of(2022, Month.MARCH, 21))
                .timePattern(TimePatternDto.builder()
                        .dailyFrequency(2)
                        .timeBasis(TimeBasis.WEEKLY)
                        .timePatternElement(daysList)
                        .build())
                .build();
    }

    @Test
    void shouldCreateEvents() {
//        List<EventDto> eventList = new ArrayList<>();
//        eventList.add(new EventDto());
        eventService.create(prescriptionDto);
//        Assertions.assertEquals();
    }
}