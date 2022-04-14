package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.constant.LogMessages;
import com.telekom.javaschool.medicalrehapp.dao.EventRepository;
import com.telekom.javaschool.medicalrehapp.dto.EventBoardDto;
import com.telekom.javaschool.medicalrehapp.dto.EventDto;
import com.telekom.javaschool.medicalrehapp.dto.EventRequestDto;
import com.telekom.javaschool.medicalrehapp.dto.EventResponseDto;
import com.telekom.javaschool.medicalrehapp.entity.EventEntity;
import com.telekom.javaschool.medicalrehapp.entity.EventStatus;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionEntity;
import com.telekom.javaschool.medicalrehapp.entity.TimeBasis;
import com.telekom.javaschool.medicalrehapp.entity.TimePatternElementEntity;
import com.telekom.javaschool.medicalrehapp.entity.TimePatternEntity;
import com.telekom.javaschool.medicalrehapp.mapper.EventBoardMapper;
import com.telekom.javaschool.medicalrehapp.mapper.EventMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class EventServiceImpl implements EventService {

    private final EventMapper eventMapper;
    private final EventBoardMapper eventBoardMapper;
    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventMapper eventMapper,
                            EventBoardMapper eventBoardMapper,
                            EventRepository eventRepository) {
        this.eventMapper = eventMapper;
        this.eventBoardMapper = eventBoardMapper;
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

    @Override
    @Transactional
    public void updateByPrescription(PrescriptionEntity prescriptionEntity) {
        log.debug("Events update started");
        List<EventEntity> eventEntityList = eventRepository.findAllByPrescription(prescriptionEntity);
        cancelEventsByReason(eventEntityList, "Prescription is updated by the doctor");
        List<LocalDateTime> dateTimeOfEvents = createDateTimeOfEvents(prescriptionEntity);
        eventEntityList.addAll(convertDateTimeToEvents(prescriptionEntity, dateTimeOfEvents));
        eventRepository.saveAll(eventEntityList);
        log.debug("Events canceled and saved to repository");
    }

    @Override
    @Transactional
    public void update(EventDto eventDto) {
        EventEntity eventEntity = getEventEntityByUuid(eventDto.getUuid().toString());
        eventEntity.setEventStatus(eventDto.getEventStatus());
        eventEntity.setCancelReason(eventDto.getCancelReason());
        eventRepository.save(eventEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public EventDto findByUuid(String uuid) {
        return eventMapper.entityToDto(getEventEntityByUuid(uuid));
    }

    @Override
    @Transactional
    public void cancelByPrescription(PrescriptionEntity prescriptionEntity) {
        List<EventEntity> eventsToCancel = eventRepository.findAllByPrescription(prescriptionEntity);
        cancelEventsByReason(eventsToCancel, "Prescription is canceled by the doctor");
        eventRepository.saveAll(eventsToCancel);
    }

    @Override
    @Transactional
    public void cancelByPatient(PatientEntity patientEntity) {
        List<EventEntity> eventsToCancel = eventRepository.findAllByPatient(patientEntity);
        cancelEventsByReason(eventsToCancel, "Patient is discharged by the doctor");
        eventRepository.saveAll(eventsToCancel);
    }

    @Override
    @Transactional(readOnly = true)
    public EventResponseDto showEventsByFilters(EventRequestDto eventRequestDto) {
        Page<EventEntity> eventEntityPage = getEventEntitiesByFilters(eventRequestDto);
        return buildEventResponseDto(eventRequestDto, eventEntityPage);
    }

    @Override
    @Transactional
    public List<EventBoardDto> getBoardEvents() {
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        LocalDateTime todayEnd = LocalDate.now().atTime(23,59);
        List<EventStatus> statuses = Collections.singletonList(EventStatus.SCHEDULED);
        Pageable pageRequest = PageRequest.of(0, Integer.MAX_VALUE);
        Page<EventEntity> eventEntityPage = eventRepository.findAllByDateTimeBetweenAndEventStatusIsInOrderByDateTimeAsc(
                todayStart, todayEnd, statuses, pageRequest);
        return eventBoardMapper.entityListToDtoList(eventEntityPage.getContent());
    }

    private Page<EventEntity> getEventEntitiesByFilters(EventRequestDto eventRequestDto) {
        int rowsOnPage = eventRequestDto.getLength();
        int pageNumber = eventRequestDto.getStart() / rowsOnPage;
        Pageable pageRequest = PageRequest.of(pageNumber, rowsOnPage);
        LocalDateTime startDateTime = LocalDateTime.parse(eventRequestDto.getStartDateTime());
        LocalDateTime endDateTime = LocalDateTime.parse(eventRequestDto.getEndDateTime());
        List<EventStatus> statuses = eventRequestDto.getStatuses();
        String patientNameLike = eventRequestDto.getSearch();
        if (patientNameLike.isBlank()) {
            return eventRepository.findAllByDateTimeBetweenAndEventStatusIsInOrderByDateTimeAsc(
                    startDateTime, endDateTime, statuses, pageRequest);
        }
        else {
            return eventRepository.findAllByDateTimeBetweenAndEventStatusIsInAndPatientNameLikeOrderByDateTimeAsc(
                    patientNameLike, startDateTime, endDateTime, statuses, pageRequest);
        }
    }

    private EventResponseDto buildEventResponseDto(EventRequestDto eventRequestDto, Page<EventEntity> eventEntityPage) {
        return EventResponseDto.builder()
                .data(eventMapper.entityListToDtoList(eventEntityPage.getContent()))
                .draw(eventRequestDto.getDraw())
                .recordsTotal((int) eventRepository.count())
                .recordsFiltered((int) eventEntityPage.getTotalElements())
                .build();
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

    private List<EventEntity> convertDateTimeToEvents(PrescriptionEntity prescriptionEntity,
                                                      List<LocalDateTime> dateTimeOfEvents) {
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
            eventEntity.setPrescription(prescriptionEntity);
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

    private EventEntity getEventEntityByUuid(String uuid) {
        return eventRepository.findByUuid(UUID.fromString(uuid))
                .orElseThrow(() -> new EntityNotFoundException(String.format(LogMessages.EVENT_NOT_FOUND, uuid)));
    }

    private void cancelEventsByReason(List<EventEntity> eventsToCancel, String reason) {
        for (EventEntity event : eventsToCancel) {
            if (event.getEventStatus() == EventStatus.SCHEDULED) {
                event.setEventStatus(EventStatus.CANCELED);
                event.setCancelReason(reason);
            }
        }
    }
}
