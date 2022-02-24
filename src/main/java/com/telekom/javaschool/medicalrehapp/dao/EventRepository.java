package com.telekom.javaschool.medicalrehapp.dao;

import com.telekom.javaschool.medicalrehapp.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}