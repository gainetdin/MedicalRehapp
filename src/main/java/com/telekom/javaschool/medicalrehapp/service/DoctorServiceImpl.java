package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dao.DoctorRepository;
import com.telekom.javaschool.medicalrehapp.dto.DoctorDto;
import com.telekom.javaschool.medicalrehapp.entity.DoctorEntity;
import com.telekom.javaschool.medicalrehapp.mapper.DoctorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

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
    @Transactional
    public void save(DoctorDto doctorDto) {
        doctorRepository.save(doctorMapper.dtoToEntity(doctorDto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorDto> findAll() {
        return doctorMapper.entityListToDtoList(doctorRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorDto findByName(String name) {
        DoctorEntity doctorEntity = doctorRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Doctor with this name doesn't exist"));
        return doctorMapper.entityToDto(doctorEntity);
    }
}
