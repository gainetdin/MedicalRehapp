package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dao.EventRepository;
import com.telekom.javaschool.medicalrehapp.dao.PrescriptionRepository;
import com.telekom.javaschool.medicalrehapp.dto.EventDto;
import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.dto.TimePatternDto;
import com.telekom.javaschool.medicalrehapp.dto.TimePatternElementDto;
import com.telekom.javaschool.medicalrehapp.entity.EventEntity;
import com.telekom.javaschool.medicalrehapp.entity.EventStatus;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionEntity;
import com.telekom.javaschool.medicalrehapp.entity.TimeBasis;
import com.telekom.javaschool.medicalrehapp.mapper.EventMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
    private final PrescriptionRepository prescriptionRepository;

    @Autowired
    public EventServiceImpl(EventMapper eventMapper,
                            EventRepository eventRepository, PrescriptionRepository prescriptionRepository) {
        this.eventMapper = eventMapper;
        this.eventRepository = eventRepository;
        this.prescriptionRepository = prescriptionRepository;
    }

    @Override
    @Transactional
    public void create(PrescriptionDto prescriptionDto) {
        log.debug("Events creation started");
        List<LocalDateTime> dateTimeOfEvents = createDateTimeOfEvents(prescriptionDto);
        List<EventDto> eventDtoList = convertDateTimeToEvents(prescriptionDto, dateTimeOfEvents);
        PrescriptionEntity prescriptionEntity = prescriptionRepository.findByUuid(prescriptionDto.getUuid())
                .orElseThrow(EntityNotFoundException::new);
        List<EventEntity> eventEntityList = eventMapper.dtoListToEntityList(eventDtoList);
        eventEntityList.forEach(entity -> {
            entity.setPatient(prescriptionEntity.getPatient());
            entity.setTreatment(prescriptionEntity.getTreatment());
        });
        eventRepository.saveAll(eventEntityList);
        log.debug("Events saved to repository");
    }

    private List<LocalDateTime> createDateTimeOfEvents(PrescriptionDto prescriptionDto) {
        List<LocalDateTime> dateTimeOfEventList = new ArrayList<>();
        TimePatternDto timePatternDto = prescriptionDto.getTimePattern();
        LocalDateTime startDateTime = prescriptionDto.getStartDateTime();
        LocalDateTime endDateTime = prescriptionDto.getEndDate().atTime(LocalTime.MAX);
        List<LocalTime> timeSchedule = getTimeSchedule(timePatternDto.getDailyFrequency());
        List<DayOfWeek> daySchedule = getDaySchedule(timePatternDto);

        LocalDate eventDate;
        LocalDateTime eventDateTime = startDateTime;
        while (eventDateTime.isBefore(endDateTime)) {
            eventDate = eventDateTime.toLocalDate();
            eventDateTime = eventDateTime.plusDays(1);
            if (timePatternDto.getTimeBasis() == TimeBasis.WEEKLY && !daySchedule.contains(eventDate.getDayOfWeek())) {
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

    private List<EventDto> convertDateTimeToEvents(PrescriptionDto prescriptionDto, List<LocalDateTime> dateTimeOfEvents) {
        List<EventDto> eventList = new ArrayList<>();
        for (LocalDateTime eventDateTime : dateTimeOfEvents) {
            EventDto eventDto = EventDto.builder()
                    .eventStatus(EventStatus.SCHEDULED)
                    .dateTime(eventDateTime)
                    .build();
            eventList.add(eventDto);
        }
        if (eventList.isEmpty()) {
            throw new IllegalArgumentException("Cannot create any event");
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

    private List<DayOfWeek> getDaySchedule(TimePatternDto timePattern) {
        List<DayOfWeek> dayOfWeekList = new ArrayList<>();
        if (timePattern.getTimeBasis() == TimeBasis.WEEKLY) {
            List<TimePatternElementDto> elementList = timePattern.getTimePatternElement();
            for (TimePatternElementDto element : elementList) {
                dayOfWeekList.add(element.getDayOfWeek());
            }
        }
        return dayOfWeekList;
    }

    @Override
    public void update(PrescriptionDto prescriptionDto) {

    }

    @Override
    public void deleteByPrescription(PrescriptionDto prescriptionDto) {

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
    public List<EventDto> showEventsByTime() {
        return null;
    }
}
