package com.telekom.javaschool.medicalrehapp.dao;

import com.telekom.javaschool.medicalrehapp.entity.TimePattern;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimePatternRepository extends JpaRepository<TimePattern, Long> {
}