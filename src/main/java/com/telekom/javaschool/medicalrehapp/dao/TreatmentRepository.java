package com.telekom.javaschool.medicalrehapp.dao;

import com.telekom.javaschool.medicalrehapp.entity.Treatment;
import org.springframework.data.repository.CrudRepository;

public interface TreatmentRepository extends CrudRepository<Treatment, Long> {
}