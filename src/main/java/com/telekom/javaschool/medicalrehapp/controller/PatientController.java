package com.telekom.javaschool.medicalrehapp.controller;

import com.telekom.javaschool.medicalrehapp.dto.DoctorDto;
import com.telekom.javaschool.medicalrehapp.dto.PatientDto;
import com.telekom.javaschool.medicalrehapp.entity.PatientStatus;
import com.telekom.javaschool.medicalrehapp.service.DoctorService;
import com.telekom.javaschool.medicalrehapp.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@PreAuthorize("hasRole('ROLE_DOCTOR')")
@Controller
@RequestMapping("/patient")
public class PatientController {

    private static final String PATIENT = "patient";
    private static final String PATIENTS = "patients";
    private static final String DOCTORS = "doctors";
    private static final String STATUSES = "statuses";
    private final PatientService patientService;
    private final DoctorService doctorService;

    @Autowired
    public PatientController(PatientService patientService, DoctorService doctorService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @GetMapping
    public String showPatients(Model model) {
        model.addAttribute(PATIENTS, patientService.findAll());
        return PATIENTS;
    }

    @GetMapping("/add")
    public String showPatientAddForm(Model model) {
        PatientDto patientDto = new PatientDto();
        List<DoctorDto> doctors = doctorService.findAll();
        model.addAttribute(PATIENT, patientDto);
        model.addAttribute(DOCTORS, doctors);
        return "patient-add";
    }

    @PostMapping("/add")
    public String addPatient(PatientDto patientDto) {
        patientService.create(patientDto);
        return "redirect:/patient";
    }

    @GetMapping("/{insuranceNumber}/edit")
    public String showPatientEditForm(@PathVariable("insuranceNumber") String insuranceNumber, Model model) {
        model.addAttribute(PATIENT, patientService.findByInsuranceNumber(insuranceNumber));
        model.addAttribute(DOCTORS, doctorService.findAll());
        model.addAttribute(STATUSES, PatientStatus.values());
        return "patient-edit";
    }

    @PostMapping
    public String editPatient(PatientDto patientDto) {
        patientService.update(patientDto);
        return "redirect:/patient";
    }
}