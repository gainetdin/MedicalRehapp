package com.telekom.javaschool.medicalrehapp.dao;

import com.telekom.javaschool.medicalrehapp.entity.Doctor;
import org.springframework.data.repository.CrudRepository;

public interface DoctorDao extends CrudRepository<Doctor, Long> {
}
