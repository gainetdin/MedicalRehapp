package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.constant.LogMessages;
import com.telekom.javaschool.medicalrehapp.dao.DoctorRepository;
import com.telekom.javaschool.medicalrehapp.dto.DoctorDto;
import com.telekom.javaschool.medicalrehapp.dto.UserDto;
import com.telekom.javaschool.medicalrehapp.entity.DoctorEntity;
import com.telekom.javaschool.medicalrehapp.entity.Role;
import com.telekom.javaschool.medicalrehapp.entity.UserEntity;
import com.telekom.javaschool.medicalrehapp.mapper.DoctorMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository, DoctorMapper doctorMapper) {
        this.doctorRepository = doctorRepository;
        this.doctorMapper = doctorMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorDto> findAll() {
        return doctorMapper.entityListToDtoList(doctorRepository.findAll());
    }

    @Override
    @Transactional
    public void checkDoctorList(UserDto previousUser, UserEntity currentUser) {
        Role previousRole = previousUser.getRole();
        Role currentRole = currentUser.getRole();
        if (previousRole != Role.DOCTOR && currentRole == Role.DOCTOR) {
            DoctorEntity doctorEntity = new DoctorEntity();
            doctorEntity.setUser(currentUser);
            doctorRepository.save(doctorEntity);
        }
        if (previousRole == Role.DOCTOR && currentRole != Role.DOCTOR) {
            log.debug("User is not doctor anymore");
            doctorRepository.deleteByUserLogin(currentUser.getLogin());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorEntity getDoctorFromSecurityContext() {
        String doctorLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        return doctorRepository.findByUserLogin(doctorLogin)
                .orElseThrow(() -> new EntityNotFoundException(String.format(LogMessages.DOCTOR_NOT_FOUND, doctorLogin)));
    }
}
