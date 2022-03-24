package com.telekom.javaschool.medicalrehapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.validation.ConstraintViolationException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionController {

    private static final String ERROR = "error";

    @ExceptionHandler({EntityExistsException.class, EntityExistsException.class,
            UsernameNotFoundException.class, IllegalArgumentException.class, ConstraintViolationException.class})
    public String expectedErrorHandler(RuntimeException exception, Model model) {
        model.addAttribute("message", exception.getMessage());
        log.error(exception.getMessage());
        return ERROR;
    }
}
