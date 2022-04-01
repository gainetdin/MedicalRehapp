package com.telekom.javaschool.medicalrehapp.controller;

import com.telekom.javaschool.medicalrehapp.dto.PatientDto;
import com.telekom.javaschool.medicalrehapp.service.PatientManager;
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

@Slf4j
@PreAuthorize("hasRole('ROLE_DOCTOR')")
@Controller
@RequestMapping("/patient")
public class PatientController {

    private static final String PATIENT = "patient";
    private static final String PATIENTS = "patients";
    private final PatientService patientService;
    private final PatientManager patientManager;

    @Autowired
    public PatientController(PatientService patientService, PatientManager patientManager) {
        this.patientService = patientService;
        this.patientManager = patientManager;
    }

    @GetMapping
    public String showPatients(Model model) {
        model.addAttribute(PATIENTS, patientService.findAll());
        return PATIENTS;
    }

    @GetMapping("/add")
    public String showPatientAddForm(Model model) {
        PatientDto patientDto = new PatientDto();
        model.addAttribute(PATIENT, patientDto);
        return "patient-add";
    }

    @PostMapping("/add")
    public String addPatient(PatientDto patientDto) {
        patientManager.createPatient(patientDto);
        return "redirect:/patient";
    }

    @GetMapping("/{insuranceNumber}/edit")
    public String showPatientEditForm(@PathVariable("insuranceNumber") String insuranceNumber, Model model) {
        model.addAttribute(PATIENT, patientService.getByInsuranceNumber(insuranceNumber));
        return "patient-edit";
    }

    @PostMapping
    public String editPatient(PatientDto patientDto) {
        patientManager.updatePatient(patientDto);
        return "redirect:/patient";
    }

    @GetMapping("/{insuranceNumber}/discharge")
    public String dischargePatient(@PathVariable("insuranceNumber") String insuranceNumber) {
        patientManager.dischargePatientAndCancelEverything(insuranceNumber);
        return "redirect:/patient";
    }

    @GetMapping("/{insuranceNumber}/take-back")
    public String takeBackPatient(@PathVariable("insuranceNumber") String insuranceNumber) {
        patientService.takeBack(insuranceNumber);
        return "redirect:/patient";
    }
}