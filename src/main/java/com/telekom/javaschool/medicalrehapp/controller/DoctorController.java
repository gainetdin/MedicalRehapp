package com.telekom.javaschool.medicalrehapp.controller;

import com.telekom.javaschool.medicalrehapp.dao.DoctorRepository;
import com.telekom.javaschool.medicalrehapp.entity.Doctor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class DoctorController {

    private final DoctorRepository doctorRepository;

    @GetMapping("/signup")
    public String showSignUpForm(Doctor doctor) {
        log.debug("Signup page requested");
        return "add-doctor";
    }

    @PostMapping("/add-doctor")
    public String addDoctor(Doctor doctor, Model model) {
        doctorRepository.save(doctor);
        log.debug("New doctor added");
        return "redirect:/";
    }

    @GetMapping("/")
    public String showDoctorList(Model model) {
        model.addAttribute("doctors", doctorRepository.findAll());
        log.debug("Index page requested");
        return "index";
    }

    @Autowired
    public DoctorController(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }
}
