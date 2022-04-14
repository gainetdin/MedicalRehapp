package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.EventBoardDto;

import java.util.List;

public interface MessageService {

    /**
     * Serializes list of events to JSON and sends to the queue.
     * @param eventBoardDtoList list of events with string fields
     */
    void sendMessage(List<EventBoardDto> eventBoardDtoList);
}
