package com.telekom.javaschool.medicalrehapp.controller;

import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.dto.TreatmentDto;
import com.telekom.javaschool.medicalrehapp.service.TreatmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@PreAuthorize("hasRole('ROLE_DOCTOR')")
@Controller
@RequestMapping("/prescription")
public class TreatmentController {

    private final TreatmentService treatmentService;
//    private final PatientService patientService;

    @Autowired
    public TreatmentController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @PostMapping("/treatment")
    public void addTreatment(TreatmentDto treatmentDto) {
        log.debug(treatmentDto.toString());
        treatmentService.create(treatmentDto);
    }
}
