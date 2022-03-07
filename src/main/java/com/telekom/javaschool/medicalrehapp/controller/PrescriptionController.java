package com.telekom.javaschool.medicalrehapp.controller;

import com.telekom.javaschool.medicalrehapp.dto.PatientDto;
import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.dto.TimePatternDto;
import com.telekom.javaschool.medicalrehapp.dto.TimePatternElementDto;
import com.telekom.javaschool.medicalrehapp.dto.TreatmentDto;
import com.telekom.javaschool.medicalrehapp.entity.TimeBasis;
import com.telekom.javaschool.medicalrehapp.entity.TreatmentType;
import com.telekom.javaschool.medicalrehapp.service.PatientService;
import com.telekom.javaschool.medicalrehapp.service.PrescriptionService;
import com.telekom.javaschool.medicalrehapp.service.TreatmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.util.ArrayList;
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

    @GetMapping
    public String addPrescription(@RequestParam String insurance, Model model) {
        PrescriptionDto prescriptionDto = new PrescriptionDto();
        TimePatternDto timePatternDto = new TimePatternDto();
        List<TimePatternElementDto> timePatternElementDtos = new ArrayList<>();
        PatientDto patientDto = patientService.findByInsuranceNumber(insurance);
        model.addAttribute("prescription", prescriptionDto);
        model.addAttribute("patient", patientDto);
        model.addAttribute("treatments", treatmentService.findAll());
        model.addAttribute("timeBasis", TimeBasis.DAILY);
        model.addAttribute("timePattern", timePatternDto);
        model.addAttribute("dayOfWeek", DayOfWeek.values());
        model.addAttribute("timePatternElements", timePatternElementDtos);
        log.debug("Prescription page requested");
        return "prescription";
    }

//    @GetMapping
//    public String editPrescription(@RequestParam String insurance, @RequestParam String id, Model model) {
//        PrescriptionDto prescriptionDto = prescriptionService.findById(id);
//        PatientDto patientDto = patientService.findByInsuranceNumber(insurance);
//        model.addAttribute("prescription", prescriptionDto);
//        model.addAttribute("patient", patientDto);
//        return "prescription";
//    }

}
