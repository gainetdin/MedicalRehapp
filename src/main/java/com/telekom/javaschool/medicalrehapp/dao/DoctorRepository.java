package com.telekom.javaschool.medicalrehapp.dao;

import com.telekom.javaschool.medicalrehapp.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
