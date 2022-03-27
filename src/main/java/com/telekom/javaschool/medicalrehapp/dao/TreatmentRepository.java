package com.telekom.javaschool.medicalrehapp.dao;

import com.telekom.javaschool.medicalrehapp.entity.TreatmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TreatmentRepository extends JpaRepository<TreatmentEntity, Long> {

    Optional<TreatmentEntity> findByName(String name);

    @Modifying
    @Query(value = "UPDATE mrschema.treatment SET deleted = true WHERE name = :name", nativeQuery = true)
    void deleteByName(@Param("name") String name);
}