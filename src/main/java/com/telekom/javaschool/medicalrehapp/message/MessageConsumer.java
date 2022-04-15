package com.telekom.javaschool.medicalrehapp.message;

import com.telekom.javaschool.medicalrehapp.service.EventManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageConsumer {

    private final EventManager eventManager;

    @Autowired
    public MessageConsumer(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * Listens to messages and sends events list by request.
     * @param message request message
     */
    @JmsListener(destination = "medrehapp-init")
    public void consumeMessage(String message) {
        log.debug("Message received: " + message);
        String requestText = "Asking for event list";
        if (requestText.equals(message)) {
            eventManager.sendEvents();
        }
    }
}
