package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.EventBoardDto;

import java.util.List;

public interface MessageService {

    void sendMessage(List<EventBoardDto> eventBoardDtoList);
}
