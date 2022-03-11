package com.telekom.javaschool.medicalrehapp.controller;

import com.telekom.javaschool.medicalrehapp.dto.PatientDto;
import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.entity.DosageUnit;
import com.telekom.javaschool.medicalrehapp.service.PatientService;
import com.telekom.javaschool.medicalrehapp.service.PrescriptionService;
import com.telekom.javaschool.medicalrehapp.service.TreatmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.DayOfWeek;
import java.util.List;

@Slf4j
@PreAuthorize("hasRole('ROLE_DOCTOR')")
@Controller
@RequestMapping("/patient/{insuranceNumber}/prescription")
public class PrescriptionController {

    private static final String PRESCRIPTION = "prescription";
    private static final String INSURANCE_NUMBER = "insuranceNumber";
    private static final String TREATMENTS = "treatments";
    private static final String DAYS_OF_WEEK = "daysOfWeek";
    private static final String DOSAGE_UNITS = "dosageUnits";
    private final PrescriptionService prescriptionService;
    private final PatientService patientService;
    private final TreatmentService treatmentService;

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService,
                                  PatientService patientService,
                                  TreatmentService treatmentService) {
        this.prescriptionService = prescriptionService;
        this.patientService = patientService;
        this.treatmentService = treatmentService;
    }

    @GetMapping
    public String showPrescriptionsOfPatient(@PathVariable(INSURANCE_NUMBER) String insuranceNumber, Model model) {
        List<PrescriptionDto> prescriptionDtos = prescriptionService.findPrescriptionsByPatient(insuranceNumber);
        PatientDto patientDto = patientService.findByInsuranceNumber(insuranceNumber);
        model.addAttribute("prescriptions", prescriptionDtos);
        model.addAttribute("patient", patientDto);
        log.debug("Prescriptions page requested");
        return "prescriptions";
    }

    @GetMapping("/add")
    public String showPrescriptionAddForm(@PathVariable(INSURANCE_NUMBER) String insuranceNumber, Model model) {
        PrescriptionDto prescriptionDto = new PrescriptionDto();
        prescriptionDto.setPatient(patientService.findByInsuranceNumber(insuranceNumber));
        model.addAttribute(PRESCRIPTION, prescriptionDto);
        model.addAttribute(TREATMENTS, treatmentService.findAll());
        model.addAttribute(DAYS_OF_WEEK, DayOfWeek.values());
        model.addAttribute(DOSAGE_UNITS, DosageUnit.values());
        return PRESCRIPTION;
    }

    @PostMapping
    public String addPrescription(@PathVariable(INSURANCE_NUMBER) String insuranceNumber,
                                   PrescriptionDto prescriptionDto) {
        log.debug(prescriptionDto.toString());
        prescriptionService.create(prescriptionDto);
        return "redirect:/patient/" + insuranceNumber + "/prescription";
    }

    @GetMapping("/{uuid}")
    public String showPrescriptionEditForm(@PathVariable("uuid") String uuid, Model model) {
        PrescriptionDto prescriptionDto = prescriptionService.findByUuid(uuid);
        log.debug(prescriptionDto.toString());
        model.addAttribute(PRESCRIPTION, prescriptionDto);
        model.addAttribute(TREATMENTS, treatmentService.findAll());
        model.addAttribute(DAYS_OF_WEEK, DayOfWeek.values());
        model.addAttribute(DOSAGE_UNITS, DosageUnit.values());
        return PRESCRIPTION;
    }

    @PostMapping("/{uuid}")
    public String editPrescription(@PathVariable(INSURANCE_NUMBER) String insuranceNumber,
                                  PrescriptionDto prescriptionDto) {
        prescriptionService.update(prescriptionDto);
        return "redirect:/patient/" + insuranceNumber + "/prescription";
    }
}
