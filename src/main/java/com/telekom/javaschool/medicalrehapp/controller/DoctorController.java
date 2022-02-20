package com.telekom.javaschool.medicalrehapp.controller;

import com.telekom.javaschool.medicalrehapp.dao.DoctorDao;
import com.telekom.javaschool.medicalrehapp.entity.Doctor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DoctorController {

    private static final Logger log = LoggerFactory.getLogger(DoctorController.class);
    private final DoctorDao doctorDao;

    @GetMapping("/signup")
    public String showSignUpForm(Doctor doctor) {
        log.debug("Signup page requested");
        return "add-doctor";
    }

    @PostMapping("/add-doctor")
    public String addDoctor(Doctor doctor, Model model) {
        doctorDao.save(doctor);
        log.debug("New doctor added");
        return "redirect:/";
    }

    @GetMapping("/")
    public String showDoctorList(Model model) {
        model.addAttribute("doctors", doctorDao.findAll());
        log.debug("Index page requested");
        return "index";
    }

    @Autowired
    public DoctorController(DoctorDao doctorDao) {
        this.doctorDao = doctorDao;
    }
}
