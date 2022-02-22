package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dao.DoctorRepository;
import com.telekom.javaschool.medicalrehapp.entity.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public void save(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    @Override
    public List<Doctor> findAll() {
        return (List<Doctor>) doctorRepository.findAll();
    }
}
