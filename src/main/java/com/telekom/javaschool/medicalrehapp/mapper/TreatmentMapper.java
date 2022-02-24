package com.telekom.javaschool.medicalrehapp.mapper;

import com.telekom.javaschool.medicalrehapp.dto.TreatmentDto;
import com.telekom.javaschool.medicalrehapp.entity.Treatment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TreatmentMapper {

    Treatment dtoToEntity(TreatmentDto dto);

    TreatmentDto entityToDto(Treatment entity);
}
