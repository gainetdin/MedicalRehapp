package com.telekom.javaschool.medicalrehapp.dao;

import com.telekom.javaschool.medicalrehapp.entity.TreatmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TreatmentRepository extends JpaRepository<TreatmentEntity, Long> {

    Optional<TreatmentEntity> findByName(String name);
}