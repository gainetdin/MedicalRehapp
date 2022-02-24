package com.telekom.javaschool.medicalrehapp.controller;

import com.telekom.javaschool.medicalrehapp.dto.DoctorDto;
import com.telekom.javaschool.medicalrehapp.service.DoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/signup")
    public String showSignUpForm(DoctorDto doctorDto) {
        log.debug("Signup page requested");
        return "add-doctor";
    }

    @PostMapping("/add-doctor")
    public String addDoctor(DoctorDto doctorDto, Model model) {
        doctorService.save(doctorDto);
        log.debug("New doctor added");
        return "redirect:/";
    }

    @GetMapping("/")
    public String showDoctorList(Model model) {
        model.addAttribute("doctors", doctorService.findAll());
        log.debug("Index page requested");
        return "index";
    }

    @GetMapping("/hello")
    public String sayHello() {
        log.debug("Hello page requested");
        return "hello";
    }
}
