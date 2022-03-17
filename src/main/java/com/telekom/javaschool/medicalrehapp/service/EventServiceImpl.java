package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dao.EventRepository;
import com.telekom.javaschool.medicalrehapp.dto.EventDto;
import com.telekom.javaschool.medicalrehapp.entity.EventEntity;
import com.telekom.javaschool.medicalrehapp.entity.EventStatus;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionEntity;
import com.telekom.javaschool.medicalrehapp.entity.TimeBasis;
import com.telekom.javaschool.medicalrehapp.entity.TimePatternElementEntity;
import com.telekom.javaschool.medicalrehapp.entity.TimePatternEntity;
import com.telekom.javaschool.medicalrehapp.mapper.EventMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class EventServiceImpl implements EventService {

    private final EventMapper eventMapper;
    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventMapper eventMapper, EventRepository eventRepository) {
        this.eventMapper = eventMapper;
        this.eventRepository = eventRepository;
    }

    @Override
    @Transactional
    public void create(PrescriptionEntity prescriptionEntity) {
        log.debug("Events creation started");
        List<LocalDateTime> dateTimeOfEvents = createDateTimeOfEvents(prescriptionEntity);
        List<EventEntity> eventEntityList = convertDateTimeToEvents(prescriptionEntity, dateTimeOfEvents);
        eventRepository.saveAll(eventEntityList);
        log.debug("Events saved to repository");
    }

    private List<LocalDateTime> createDateTimeOfEvents(PrescriptionEntity prescriptionEntity) {
        List<LocalDateTime> dateTimeOfEventList = new ArrayList<>();
        TimePatternEntity timePatternEntity = prescriptionEntity.getTimePattern();
        LocalDateTime startDateTime = prescriptionEntity.getStartDateTime();
        LocalDateTime endDateTime = prescriptionEntity.getEndDate().atTime(LocalTime.MAX);
        List<LocalTime> timeSchedule = getTimeSchedule(timePatternEntity.getDailyFrequency());
        List<DayOfWeek> daySchedule = getDaySchedule(timePatternEntity);
        LocalDate eventDate;
        LocalDateTime eventDateTime = startDateTime;
        while (eventDateTime.isBefore(endDateTime)) {
            eventDate = eventDateTime.toLocalDate();
            eventDateTime = eventDateTime.plusDays(1);
            if (timePatternEntity.getTimeBasis() == TimeBasis.WEEKLY && !daySchedule.contains(eventDate.getDayOfWeek())) {
                continue;
            }
            for (LocalTime eventTime : timeSchedule) {
                if (eventDate.atTime(eventTime).isAfter(startDateTime)) {
                    dateTimeOfEventList.add(eventDate.atTime(eventTime));
                }
            }
        }
        log.debug(dateTimeOfEventList.toString());
        return dateTimeOfEventList;
    }

    private List<EventEntity> convertDateTimeToEvents(PrescriptionEntity prescriptionEntity, List<LocalDateTime> dateTimeOfEvents) {
        if (dateTimeOfEvents.isEmpty()) {
            throw new IllegalArgumentException("Cannot create any event");
        }
        List<EventEntity> eventList = new ArrayList<>();
        for (LocalDateTime eventDateTime : dateTimeOfEvents) {
            EventEntity eventEntity = new EventEntity();
            eventEntity.setEventStatus(EventStatus.SCHEDULED);
            eventEntity.setDateTime(eventDateTime);
            eventEntity.setPatient(prescriptionEntity.getPatient());
            eventEntity.setTreatment(prescriptionEntity.getTreatment());
            eventList.add(eventEntity);
        }
        return eventList;
    }

    private List<LocalTime> getTimeSchedule(int dailyFrequency) {
        List<LocalTime> timeList = new ArrayList<>();
        LocalTime firstEventTime = LocalTime.of(8, 0);
        LocalTime lastEventTime = LocalTime.of(20, 0);
        if (dailyFrequency > 1) {
            Duration timeBetweenEvents = Duration.between(firstEventTime, lastEventTime).dividedBy(dailyFrequency - 1);
            LocalTime nextEventTime = firstEventTime;
            for (int i = 0; i < dailyFrequency; i++) {
                timeList.add(nextEventTime);
                nextEventTime = nextEventTime.plus(timeBetweenEvents);
            }
        }
        else {
            timeList.add(firstEventTime);
        }
        return timeList;
    }

    private List<DayOfWeek> getDaySchedule(TimePatternEntity timePattern) {
        List<DayOfWeek> dayOfWeekList = new ArrayList<>();
        if (timePattern.getTimeBasis() == TimeBasis.WEEKLY) {
            List<TimePatternElementEntity> elementList = timePattern.getTimePatternElement();
            for (TimePatternElementEntity element : elementList) {
                dayOfWeekList.add(element.getDayOfWeek());
            }
        }
        return dayOfWeekList;
    }

    @Override
    public void update(PrescriptionEntity prescriptionEntity) {

    }

    @Override
    public void deleteByPrescription(PrescriptionEntity prescriptionEntity) {

    }

    @Override
    public List<EventDto> showAllEvents() {
        return eventMapper.entityListToDtoList(eventRepository.findAll());
    }

    @Override
    public List<EventDto> showEventsByPatient() {
        return null;
    }

    @Override
    public List<EventDto> showEventsByDateTime() {
        return eventMapper.entityListToDtoList(eventRepository.findAllByOrderByDateTimeAsc());
    }
}
