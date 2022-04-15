package com.telekom.javaschool.medicalrehapp.mapper;

import com.telekom.javaschool.medicalrehapp.dto.EventBoardDto;
import com.telekom.javaschool.medicalrehapp.entity.EventEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventBoardMapper {

    @Mapping(source = "patient.name", target = "patientName")
    @Mapping(source = "treatment.name", target = "treatmentName")
    EventBoardDto entityToDto(EventEntity entity);

    @InheritConfiguration
    List<EventBoardDto> entityListToDtoList(List<EventEntity> entityList);
}
