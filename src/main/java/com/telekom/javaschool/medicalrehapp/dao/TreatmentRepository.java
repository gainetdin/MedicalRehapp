package com.telekom.javaschool.medicalrehapp.dao;

import com.telekom.javaschool.medicalrehapp.entity.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
}