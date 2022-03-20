package com.telekom.javaschool.medicalrehapp.controller;

import com.telekom.javaschool.medicalrehapp.dto.EventDto;
import com.telekom.javaschool.medicalrehapp.entity.EventStatus;
import com.telekom.javaschool.medicalrehapp.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@PreAuthorize("hasRole('ROLE_NURSE') or hasRole('ROLE_DOCTOR')")
@Controller
@RequestMapping("/event")
public class EventController {

    private static final String FILTER_DATE_TIME_EVENT = "filterDateTimeEvent";
    private static final String EVENTS = "events";
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public String showAllEvents(@RequestParam(value = "insuranceNumber", required = false) String insuranceNumber,
                                Model model) {
        List<EventDto> events;
        if (insuranceNumber == null) {
            events = eventService.showAllEvents();
        }
        else {
            events = eventService.showEventsByInsuranceNumber(insuranceNumber);
        }
        EventDto filterDateTimeEvent = new EventDto();
        filterDateTimeEvent.setDateTime(LocalDateTime.now());
        model.addAttribute(EVENTS, events);
        model.addAttribute(FILTER_DATE_TIME_EVENT, filterDateTimeEvent);
        return EVENTS;
    }

    @PostMapping
    public String filterEvents(Model model, EventDto filterDateTimeEvent) {
        List<EventDto> filteredEvents = eventService.showAllEventsFilteredByDateTime(filterDateTimeEvent.getDateTime());
        model.addAttribute(EVENTS, filteredEvents);
        model.addAttribute(FILTER_DATE_TIME_EVENT, filterDateTimeEvent);
        return EVENTS;
    }

    @GetMapping("/{uuid}")
    public String showEvent(@PathVariable("uuid") String uuid, Model model) {
        EventDto eventDto = eventService.findByUuid(uuid);
        model.addAttribute("event", eventDto);
        model.addAttribute("eventStatus", EventStatus.values());
        return "event";
    }

    @PostMapping("/{uuid}")
    public String editEvent(EventDto eventDto) {
        eventService.update(eventDto);
        return "redirect:/event";
    }
}
