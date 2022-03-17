package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dao.PrescriptionRepository;
import com.telekom.javaschool.medicalrehapp.dto.EventDto;
import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.dto.TimePatternDto;
import com.telekom.javaschool.medicalrehapp.dto.TimePatternElementDto;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionEntity;
import com.telekom.javaschool.medicalrehapp.entity.TimeBasis;
import com.telekom.javaschool.medicalrehapp.entity.TimePatternElementEntity;
import com.telekom.javaschool.medicalrehapp.entity.TimePatternEntity;
import com.telekom.javaschool.medicalrehapp.mapper.EventMapper;
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
    @Mock
    private PrescriptionRepository prescriptionRepository;
    @Mock
    private EventMapper eventMapper;
    private static PrescriptionEntity prescriptionEntity;
    @InjectMocks
    private EventServiceImpl eventService;

    @BeforeAll
    static void initPrescription() {
        List<TimePatternElementEntity> daysList = new ArrayList<>();
        TimePatternElementEntity monday = new TimePatternElementEntity();
        monday.setDayOfWeek(DayOfWeek.MONDAY);
        TimePatternElementEntity tuesday = new TimePatternElementEntity();
        tuesday.setDayOfWeek(DayOfWeek.TUESDAY);
        daysList.add(monday);
        daysList.add(tuesday);

        prescriptionEntity = new PrescriptionEntity();
        prescriptionEntity.setStartDateTime(LocalDateTime.of(2022, Month.MARCH, 14, 15, 0));
        prescriptionEntity.setEndDate(LocalDate.of(2022, Month.MARCH, 28));
        TimePatternEntity timePattern = new TimePatternEntity();
        timePattern.setDailyFrequency(2);
        timePattern.setTimeBasis(TimeBasis.WEEKLY);
        timePattern.setTimePatternElement(daysList);
        prescriptionEntity.setTimePattern(timePattern);
    }

    @Test
    void shouldCreateEvents() {
//        eventService.create(prescriptionEntity);
//        Assertions.assertEquals();
    }
}