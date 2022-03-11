package com.telekom.javaschool.medicalrehapp.dao;

import com.telekom.javaschool.medicalrehapp.entity.TimePatternEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimePatternRepository extends JpaRepository<TimePatternEntity, Long> {
}