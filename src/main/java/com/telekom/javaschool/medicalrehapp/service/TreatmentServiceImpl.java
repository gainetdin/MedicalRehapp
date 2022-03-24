package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.constant.LogMessages;
import com.telekom.javaschool.medicalrehapp.dao.TreatmentRepository;
import com.telekom.javaschool.medicalrehapp.dto.TreatmentDto;
import com.telekom.javaschool.medicalrehapp.mapper.TreatmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.List;

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
        String name = treatmentDto.getName();
        if (treatmentRepository.findByName(name).isPresent()) {
            throw new EntityExistsException(String.format(LogMessages.TREATMENT_EXISTS, name));
        }
        treatmentRepository.save(treatmentMapper.dtoToEntity(treatmentDto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TreatmentDto> findAll() {
        return treatmentMapper.entityListToDtoList(treatmentRepository.findAll());
    }
}
