package com.telekom.javaschool.medicalrehapp.dao;

import com.telekom.javaschool.medicalrehapp.entity.Prescription;
import org.springframework.data.repository.CrudRepository;

public interface PrescriptionRepository extends CrudRepository<Prescription, Long> {
}