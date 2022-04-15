package com.telekom.javaschool.medicalrehapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.telekom.javaschool.medicalrehapp.dto.EventBoardDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Queue;
import java.util.List;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    private final JmsTemplate jmsTemplate;

    private final Queue queue;

    @Autowired
    public MessageServiceImpl(JmsTemplate jmsTemplate, Queue queue) {
        this.jmsTemplate = jmsTemplate;
        this.queue = queue;
    }

    @Override
    public void sendMessage(List<EventBoardDto> eventBoardDtoList) {
        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        try {
            String eventsAsJson = mapper.writeValueAsString(eventBoardDtoList);
            log.debug(eventsAsJson);
            jmsTemplate.convertAndSend(queue, eventsAsJson);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        log.debug("Message sent");
    }
}