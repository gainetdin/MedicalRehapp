package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.DoctorDto;
import com.telekom.javaschool.medicalrehapp.dto.UserDto;
import com.telekom.javaschool.medicalrehapp.entity.DoctorEntity;
import com.telekom.javaschool.medicalrehapp.entity.UserEntity;

import java.util.List;

public interface DoctorService {

    /**
     * Finds all doctors from repository.
     * @return list of doctors
     */
    List<DoctorDto> findAll();

    /**
     * Checks if there are change of doctor's list after user role update.
     * In case of doctor role changes makes change in doctor repository.
     * @param previousUser  user before update
     * @param currentUser   user after update
     */
    void checkDoctorList(UserDto previousUser, UserEntity currentUser);

    /**
     * Gets doctor entity from security context for patient entity.
     * @return doctor entity (current user with doctor role)
     */
    DoctorEntity getDoctorFromSecurityContext();
}
