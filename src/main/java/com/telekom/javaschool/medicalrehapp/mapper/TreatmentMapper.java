package com.telekom.javaschool.medicalrehapp.mapper;

import com.telekom.javaschool.medicalrehapp.dto.TreatmentDto;
import com.telekom.javaschool.medicalrehapp.entity.TreatmentEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TreatmentMapper {

    TreatmentEntity dtoToEntity(TreatmentDto dto);

    TreatmentDto entityToDto(TreatmentEntity entity);

    List<TreatmentEntity> dtoListToEntityList(List<TreatmentDto> dtoList);

    List<TreatmentDto> entityListToDtoList(List<TreatmentEntity> entityList);
}
