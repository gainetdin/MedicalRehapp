package com.telekom.javaschool.medicalrehapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(Exception.class)
    public String defaultErrorHandler(Exception exception, Model model) {
        model.addAttribute("exception", exception);
        log.error(exception.getMessage());
        return "error";
    }
}
