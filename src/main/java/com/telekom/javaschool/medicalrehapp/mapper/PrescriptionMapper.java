package com.telekom.javaschool.medicalrehapp.mapper;

import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.entity.PrescriptionEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PrescriptionMapper {

    PrescriptionEntity dtoToEntity(PrescriptionDto dto);

    PrescriptionDto entityToDto(PrescriptionEntity entity);

    List<PrescriptionEntity> dtoListToEntityList(List<PrescriptionDto> dtoList);

    List<PrescriptionDto> entityListToDtoList(List<PrescriptionEntity> entityList);
}
