package com.telekom.javaschool.medicalrehapp.controller;

import com.telekom.javaschool.medicalrehapp.dto.DoctorDto;
import com.telekom.javaschool.medicalrehapp.dto.PatientDto;
import com.telekom.javaschool.medicalrehapp.service.DoctorService;
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

import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;
    private final DoctorService doctorService;

    @Autowired
    public PatientController(PatientService patientService, DoctorService doctorService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @GetMapping("/add")
    public String addNewPatient(Model model) {
        PatientDto patientDto = new PatientDto();
        List<DoctorDto> doctors = doctorService.findAll();
        model.addAttribute("patient", patientDto);
        model.addAttribute("doctors", doctors);
        return "patient-add";
    }

    @PostMapping("/add")
    public RedirectView addPatient(PatientDto patientDto, RedirectAttributes attributes) {
        patientService.create(patientDto);
        attributes.addAttribute("id", patientDto.getInsuranceNumber());
        return new RedirectView("/patient");
    }

    @GetMapping
    public String editPatient(@RequestParam(required = false) String id, Model model) {
        model.addAttribute("patient", patientService.findByInsuranceNumber(id));
        return "patient";
    }

}
