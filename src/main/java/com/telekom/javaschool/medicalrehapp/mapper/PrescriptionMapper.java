package com.telekom.javaschool.medicalrehapp.mapper;

import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.entity.Prescription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrescriptionMapper {

    Prescription dtoToEntity(PrescriptionDto dto);

    PrescriptionDto entityToDto(Prescription entity);
}
