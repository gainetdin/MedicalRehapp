package com.telekom.javaschool.medicalrehapp.controller;

import com.telekom.javaschool.medicalrehapp.dto.TreatmentDto;
import com.telekom.javaschool.medicalrehapp.entity.TreatmentType;
import com.telekom.javaschool.medicalrehapp.service.TreatmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/treatment")
public class TreatmentController {

    private final TreatmentService treatmentService;
    private String previousReference;

    @Autowired
    public TreatmentController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @GetMapping("/add")
    public String showTreatmentAddForm(@RequestHeader("referer") String referer, Model model) {
        TreatmentDto treatmentDto = new TreatmentDto();
        model.addAttribute("treatment", treatmentDto);
        model.addAttribute("treatmentTypes", TreatmentType.values());
        previousReference = referer;
        return "treatment-add";
    }

    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @PostMapping("/add")
    public String addTreatment(TreatmentDto treatmentDto) {
        log.debug(treatmentDto.toString());
        treatmentService.create(treatmentDto);
        return "redirect:" + previousReference;
    }

    @GetMapping
    public String showTreatments(Model model) {
        model.addAttribute("treatments", treatmentService.findAll());
        return "treatments";
    }
}
