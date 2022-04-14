package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dao.TimePatternElementRepository;
import com.telekom.javaschool.medicalrehapp.dao.TimePatternRepository;
import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.entity.TimeBasis;
import com.telekom.javaschool.medicalrehapp.entity.TimePatternElementEntity;
import com.telekom.javaschool.medicalrehapp.entity.TimePatternEntity;
import com.telekom.javaschool.medicalrehapp.mapper.TimePatternMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TimePatternServiceTest {

    @InjectMocks
    private TimePatternServiceImpl timePatternService;

    @Mock
    private TimePatternRepository timePatternRepository;

    @Mock
    private TimePatternMapper timePatternMapper;

    @Mock
    private TimePatternElementRepository timePatternElementRepository;

    @Captor
    private ArgumentCaptor<TimePatternEntity> timePatternEntityCaptor;

    private static TimePatternEntity timePatternEntity;

    @BeforeAll
    static void initData() {
        TimePatternElementEntity timePatternElementEntity = new TimePatternElementEntity();
        timePatternElementEntity.setDayOfWeek(DayOfWeek.TUESDAY);
        List<TimePatternElementEntity> timePatternElementEntities = new ArrayList<>();
        timePatternElementEntities.add(timePatternElementEntity);
        timePatternElementEntities.add(new TimePatternElementEntity());
        timePatternEntity = new TimePatternEntity();
        timePatternEntity.setTimeBasis(TimeBasis.WEEKLY);
        timePatternEntity.setTimePatternElement(timePatternElementEntities);
    }

    @Test
    void shouldRemoveElementsWithoutDayOfWeekOnCreate() {
        Mockito.when(timePatternMapper.dtoToEntity(Mockito.any())).thenReturn(timePatternEntity);
        timePatternService.create(new PrescriptionDto());
        Mockito.verify(timePatternRepository).save(timePatternEntityCaptor.capture());
        List<TimePatternElementEntity> elementsActual = timePatternEntityCaptor.getValue().getTimePatternElement();

        assertEquals(1, elementsActual.size());
        assertEquals(DayOfWeek.TUESDAY, elementsActual.get(0).getDayOfWeek());
    }
}