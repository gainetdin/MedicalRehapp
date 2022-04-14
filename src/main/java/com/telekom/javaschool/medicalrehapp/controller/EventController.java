package com.telekom.javaschool.medicalrehapp.controller;

import com.telekom.javaschool.medicalrehapp.dto.EventDto;
import com.telekom.javaschool.medicalrehapp.dto.EventRequestDto;
import com.telekom.javaschool.medicalrehapp.dto.EventResponseDto;
import com.telekom.javaschool.medicalrehapp.entity.EventStatus;
import com.telekom.javaschool.medicalrehapp.service.EventManager;
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
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@PreAuthorize("hasRole('ROLE_NURSE') or hasRole('ROLE_DOCTOR')")
@Controller
@RequestMapping("/event")
public class EventController {

    private static final String EVENT = "event";
    private final EventService eventService;
    private final EventManager eventManager;

    @Autowired
    public EventController(EventService eventService, EventManager eventManager) {
        this.eventService = eventService;
        this.eventManager = eventManager;
    }

    @GetMapping
    public String showEventsPage() {
        return "events";
    }

    @PostMapping("/all")
    @ResponseBody
    public EventResponseDto showEventsData(EventRequestDto eventRequestDto) {
        return eventService.showEventsByFilters(eventRequestDto);
    }

    @GetMapping("/{uuid}")
    public String showEvent(@PathVariable("uuid") String uuid, Model model) {
        EventDto eventDto = eventService.findByUuid(uuid);
        model.addAttribute(EVENT, eventDto);
        model.addAttribute("eventStatus", EventStatus.values());
        return EVENT;
    }

    @PostMapping("/{uuid}")
    public String editEvent(EventDto eventDto) {
        eventManager.updateEvent(eventDto);
        return "redirect:/event";
    }
}
