package com.telekom.javaschool.medicalrehapp.dao;

import com.telekom.javaschool.medicalrehapp.entity.TimePatternElementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimePatternElementRepository extends JpaRepository<TimePatternElementEntity, Long> {
}