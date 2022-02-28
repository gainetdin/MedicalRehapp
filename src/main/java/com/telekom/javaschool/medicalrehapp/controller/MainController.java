package com.telekom.javaschool.medicalrehapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/")
    public String showIndex() {
        log.debug("Index page requested");
        return "index";
    }

    @GetMapping("/hello")
    public String sayHello() {
        log.debug("Hello page requested");
        return "hello";
    }
}
