package com.telekom.javaschool.medicalrehapp.mapper;

import com.telekom.javaschool.medicalrehapp.dto.EventDto;
import com.telekom.javaschool.medicalrehapp.entity.EventEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventEntity dtoToEntity(EventDto dto);

    EventDto entityToDto(EventEntity entity);

    List<EventEntity> dtoListToEntityList(List<EventDto> dtoList);

    List<EventDto> entityListToDtoList(List<EventEntity> entityList);
}
