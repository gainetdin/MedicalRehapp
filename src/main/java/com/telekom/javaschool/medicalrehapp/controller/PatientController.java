package com.telekom.javaschool.medicalrehapp.controller;

import com.telekom.javaschool.medicalrehapp.dto.PatientDto;
import com.telekom.javaschool.medicalrehapp.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/add")
    public String addNewPatient() {

        return "add-patient";
    }

    @PostMapping("/add")
    public RedirectView addPatient(PatientDto patientDto, RedirectAttributes attributes) {
        patientService.create(patientDto);
        attributes.addAttribute("id", patientDto.getInsuranceNumber());
        return new RedirectView();
    }

    @GetMapping
    public String editPatient(@RequestParam String id, Model model) {
        model.addAttribute("patient", patientService.findByInsuranceNumber(id));
        return "patient";
    }

}
