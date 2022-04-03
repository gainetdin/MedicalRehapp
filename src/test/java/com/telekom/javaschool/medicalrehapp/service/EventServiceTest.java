package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dao.EventRepository;
import com.telekom.javaschool.medicalrehapp.dto.EventDto;
import com.telekom.javaschool.medicalrehapp.entity.EventEntity;
import com.telekom.javaschool.medicalrehapp.entity.EventStatus;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionEntity;
import com.telekom.javaschool.medicalrehapp.entity.TimeBasis;
import com.telekom.javaschool.medicalrehapp.entity.TimePatternElementEntity;
import com.telekom.javaschool.medicalrehapp.entity.TimePatternEntity;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @InjectMocks
    private EventServiceImpl eventService;

    @Mock
    private EventRepository eventRepository;

    @Captor
    private ArgumentCaptor<List<EventEntity>> entitiesCaptor;

    @Captor
    private ArgumentCaptor<EventEntity> eventCaptor;

    private static PrescriptionEntity prescriptionEntity;
    private static List<LocalDateTime> dateTimesExpected;

    @BeforeAll
    static void initData() {
        createPrescriptionEntity();
        createDateTimesExpected();
    }

    @Test
    void shouldCreateEvents() {
        eventService.create(prescriptionEntity);
        Mockito.verify(eventRepository).saveAll(entitiesCaptor.capture());
        List<EventEntity> eventsActual = entitiesCaptor.getValue();
        List<LocalDateTime> dateTimesActual = eventsActual.stream()
                .map(EventEntity::getDateTime)
                .collect(Collectors.toList());

        assertEquals(dateTimesExpected, dateTimesActual);
    }

    @Test
    void shouldCreateNewEventsOnUpdate() {
        List<EventEntity> eventsActual = getActualEventsOnUpdate();
        List<LocalDateTime> dateTimesActual = eventsActual.stream()
                .filter(event -> event.getEventStatus() == EventStatus.SCHEDULED)
                .map(EventEntity::getDateTime)
                .collect(Collectors.toList());

        assertEquals(dateTimesExpected, dateTimesActual);
    }

    @Test
    void shouldCancelOldEventsOnUpdate() {
        List<EventEntity> eventsActual = getActualEventsOnUpdate();
        List<LocalDateTime> dateTimesActual = eventsActual.stream()
                .filter(event -> event.getEventStatus() == EventStatus.CANCELED)
                .map(EventEntity::getDateTime)
                .collect(Collectors.toList());

        assertEquals(dateTimesExpected, dateTimesActual);
    }

    @Test
    void shouldChangeEventStatus() {
        EventEntity oldEventEntity = new EventEntity();
        oldEventEntity.setEventStatus(EventStatus.SCHEDULED);
        Mockito.when(eventRepository.findByUuid(Mockito.any())).thenReturn(Optional.of(oldEventEntity));

        EventDto eventDto = new EventDto();
        eventDto.setUuid(UUID.randomUUID());
        eventDto.setEventStatus(EventStatus.COMPLETED);
        eventService.update(eventDto);
        Mockito.verify(eventRepository).save(eventCaptor.capture());
        EventEntity newEventEntity = eventCaptor.getValue();

        assertEquals(EventStatus.COMPLETED, newEventEntity.getEventStatus());
    }

    @Test
    void shouldCancelEventsByCancelingPrescription() {
        List<EventEntity> events = getScheduledEventEntities();
        Mockito.when(eventRepository.findAllByPrescription(Mockito.any())).thenReturn(events);

        eventService.cancelByPrescription(new PrescriptionEntity());
        Mockito.verify(eventRepository).saveAll(entitiesCaptor.capture());
        List<EventEntity> updatedEvents = entitiesCaptor.getValue();

        assertEquals(EventStatus.CANCELED, updatedEvents.get(0).getEventStatus());
    }

    @Test
    void shouldCancelEventsByDischargingPatient() {
        List<EventEntity> events = getScheduledEventEntities();
        Mockito.when(eventRepository.findAllByPatient(Mockito.any())).thenReturn(events);

        eventService.cancelByPatient(new PatientEntity());
        Mockito.verify(eventRepository).saveAll(entitiesCaptor.capture());
        List<EventEntity> updatedEvents = entitiesCaptor.getValue();

        assertEquals(EventStatus.CANCELED, updatedEvents.get(0).getEventStatus());
    }

    private static void createPrescriptionEntity() {
        TimePatternElementEntity monday = new TimePatternElementEntity();
        monday.setDayOfWeek(DayOfWeek.MONDAY);
        TimePatternElementEntity tuesday = new TimePatternElementEntity();
        tuesday.setDayOfWeek(DayOfWeek.TUESDAY);
        List<TimePatternElementEntity> daysList = new ArrayList<>();
        daysList.add(monday);
        daysList.add(tuesday);

        prescriptionEntity = new PrescriptionEntity();
        prescriptionEntity.setStartDateTime(LocalDateTime.of(2022, Month.MARCH, 14, 15, 0));
        prescriptionEntity.setEndDate(LocalDate.of(2022, Month.MARCH, 21));
        TimePatternEntity timePattern = new TimePatternEntity();
        timePattern.setDailyFrequency(1);
        timePattern.setTimeBasis(TimeBasis.WEEKLY);
        timePattern.setTimePatternElement(daysList);
        prescriptionEntity.setTimePattern(timePattern);
    }

    private static void createDateTimesExpected() {
        dateTimesExpected = new ArrayList<>();
        dateTimesExpected.add(LocalDateTime.of(2022, Month.MARCH, 15, 8, 0));
        dateTimesExpected.add(LocalDateTime.of(2022, Month.MARCH, 21, 8, 0));
    }

    private List<EventEntity> getActualEventsOnUpdate() {
        List<EventEntity> oldEvents = dateTimesExpected.stream()
                .map(this::createEventEntity)
                .collect(Collectors.toList());

        Mockito.when(eventRepository.findAllByPrescription(Mockito.any())).thenReturn(oldEvents);
        eventService.updateByPrescription(prescriptionEntity);
        Mockito.verify(eventRepository).saveAll(entitiesCaptor.capture());
        return entitiesCaptor.getValue();
    }


    private EventEntity createEventEntity(LocalDateTime dateTime) {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setDateTime(dateTime);
        eventEntity.setEventStatus(EventStatus.SCHEDULED);
        return eventEntity;
    }

    private List<EventEntity> getScheduledEventEntities() {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventStatus(EventStatus.SCHEDULED);
        List<EventEntity> events = new ArrayList<>();
        events.add(eventEntity);
        return events;
    }
}