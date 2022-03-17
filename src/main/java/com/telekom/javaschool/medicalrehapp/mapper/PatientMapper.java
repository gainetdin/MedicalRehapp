package com.telekom.javaschool.medicalrehapp.mapper;

import com.telekom.javaschool.medicalrehapp.dto.PatientDto;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientEntity dtoToEntity(PatientDto dto);

    PatientDto entityToDto(PatientEntity entity);

    List<PatientEntity> dtoListToEntityList(List<PatientDto> dtoList);

    List<PatientDto> entityListToDtoList(List<PatientEntity> entityList);
}
