package com.telekom.javaschool.medicalrehapp.dao;

import com.telekom.javaschool.medicalrehapp.entity.TimePattern;
import org.springframework.data.repository.CrudRepository;

public interface TimePatternRepository extends CrudRepository<TimePattern, Long> {
}