package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.DoctorDto;

import java.util.List;

public interface DoctorService {

    void save(DoctorDto doctor);

    List<DoctorDto> findAll();

    DoctorDto find(String name);
}
