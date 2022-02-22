package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.entity.Doctor;

import java.util.List;

public interface DoctorService {

    void save(Doctor doctor);

    List<Doctor> findAll();
}
