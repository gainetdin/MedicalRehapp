package com.telekom.javaschool.medicalrehapp.mapper;

import com.telekom.javaschool.medicalrehapp.dto.PatientDto;
import com.telekom.javaschool.medicalrehapp.entity.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    Patient dtoToEntity(PatientDto dto);

    PatientDto entityToDto(Patient entity);
}
