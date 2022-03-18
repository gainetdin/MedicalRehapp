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

import java.util.List;

@Slf4j
@PreAuthorize("hasRole('ROLE_NURSE') or hasRole('ROLE_DOCTOR')")
@Controller
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public String showAllEvents(Model model) {
        List<EventDto> allEvents = eventService.showEventsByDateTime();
        model.addAttribute("allEvents", allEvents);
        log.debug("Event page requested");
        return "events";
    }

    @GetMapping("/{uuid}")
    public String showEvent(@PathVariable("uuid") String uuid, Model model) {
        EventDto eventDto = eventService.findByUuid(uuid);
        model.addAttribute("event", eventDto);
        model.addAttribute("eventStatus", EventStatus.values());
        return "event";
    }

    @PostMapping
    public String editEvent(EventDto eventDto) {
        eventService.update(eventDto);
        return "redirect:/event";
    }
}
