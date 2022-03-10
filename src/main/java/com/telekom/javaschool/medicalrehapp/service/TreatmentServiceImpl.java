package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dao.TreatmentRepository;
import com.telekom.javaschool.medicalrehapp.dto.TreatmentDto;
import com.telekom.javaschool.medicalrehapp.mapper.TreatmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TreatmentServiceImpl implements TreatmentService {

    private final TreatmentRepository treatmentRepository;
    private final TreatmentMapper treatmentMapper;

    @Autowired
    public TreatmentServiceImpl(TreatmentRepository treatmentRepository,
                                TreatmentMapper treatmentMapper) {
        this.treatmentRepository = treatmentRepository;
        this.treatmentMapper = treatmentMapper;
    }

    @Override
    @Transactional
    public void create(TreatmentDto treatmentDto) {

    }

    @Override
    @Transactional
    public void update(TreatmentDto treatmentDto) {

    }

    @Override
    @Transactional(readOnly = true)
    public List<TreatmentDto> findAll() {
        return treatmentRepository.findAll()
                .stream()
                .map(treatmentMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TreatmentDto findById(String id) {
        return null;
    }
}
