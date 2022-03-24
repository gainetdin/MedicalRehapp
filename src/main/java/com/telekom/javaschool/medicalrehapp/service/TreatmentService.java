package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.TreatmentDto;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface TreatmentService {

    void create(@Valid TreatmentDto treatmentDto);

    List<TreatmentDto> findAll();
}
