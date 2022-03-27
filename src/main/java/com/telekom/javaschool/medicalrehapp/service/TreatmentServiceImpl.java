package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.constant.LogMessages;
import com.telekom.javaschool.medicalrehapp.dao.TreatmentRepository;
import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.dto.TreatmentDto;
import com.telekom.javaschool.medicalrehapp.entity.TreatmentEntity;
import com.telekom.javaschool.medicalrehapp.mapper.TreatmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
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

    @Override
    @Transactional
    public void delete(String name) {
        treatmentRepository.deleteByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public TreatmentEntity getByPrescription(PrescriptionDto prescriptionDto) {
        String name = prescriptionDto.getTreatment().getName();
        return treatmentRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(String.format(LogMessages.TREATMENT_NOT_FOUND, name)));
    }
}
