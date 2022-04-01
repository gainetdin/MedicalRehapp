package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.DoctorDto;
import com.telekom.javaschool.medicalrehapp.dto.UserDto;
import com.telekom.javaschool.medicalrehapp.entity.DoctorEntity;
import com.telekom.javaschool.medicalrehapp.entity.UserEntity;

import java.util.List;

public interface DoctorService {

    void save(DoctorDto doctor);

    List<DoctorDto> findAll();

    void checkDoctorList(UserDto previousUser, UserEntity currentUser);

    DoctorEntity getDoctorFromSecurityContext();
}
