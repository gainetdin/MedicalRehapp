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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.time.DayOfWeek;
import java.util.List;

@Slf4j
@PreAuthorize("hasRole('ROLE_DOCTOR')")
@Controller
@RequestMapping("/prescription")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final PatientService patientService;
    private final TreatmentService treatmentService;

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService, PatientService patientService, TreatmentService treatmentService) {
        this.prescriptionService = prescriptionService;
        this.patientService = patientService;
        this.treatmentService = treatmentService;
    }

    @GetMapping("/add")
    public String addPrescription(@RequestParam String insurance, Model model) {
        PrescriptionDto prescriptionDto = new PrescriptionDto();
        prescriptionDto.setPatient(patientService.findByInsuranceNumber(insurance));
        model.addAttribute("prescription", prescriptionDto);
        model.addAttribute("treatments", treatmentService.findAll());
        model.addAttribute("dayOfWeek", DayOfWeek.values());
        model.addAttribute("dosageUnits", DosageUnit.values());
        return "prescription";
    }

    @PostMapping
    public RedirectView savePrescription(PrescriptionDto prescriptionDto, RedirectAttributes attributes) {
        log.debug(prescriptionDto.toString());
        prescriptionService.create(prescriptionDto);
        attributes.addAttribute("insurance", prescriptionDto.getPatient().getInsuranceNumber());
        return new RedirectView("/prescription");
    }

    @GetMapping
    public String viewPrescription(@RequestParam String insurance, Model model) {
        List<PrescriptionDto> prescriptionDtos = prescriptionService.findPrescriptionsByPatient(insurance);
        PatientDto patientDto = patientService.findByInsuranceNumber(insurance);
        model.addAttribute("prescriptions", prescriptionDtos);
        model.addAttribute("patient", patientDto);
        log.debug("Prescriptions page requested");
        return "prescriptions";
    }
}
