package com.telekom.javaschool.medicalrehapp.dao;

import com.telekom.javaschool.medicalrehapp.entity.DoctorEntity;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {

    @Query(value = "SELECT * FROM mrschema.doctor d INNER JOIN mrschema.user u ON d.user_id = u.id " +
            "WHERE u.login = :login", nativeQuery = true)
    Optional<DoctorEntity> findByUserLogin(@Param("login") String login);
}
