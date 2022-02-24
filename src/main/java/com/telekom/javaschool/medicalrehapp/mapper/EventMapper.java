package com.telekom.javaschool.medicalrehapp.mapper;

import com.telekom.javaschool.medicalrehapp.dto.EventDto;
import com.telekom.javaschool.medicalrehapp.entity.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {

    Event dtoToEntity(EventDto dto);

    EventDto entityToDto(Event entity);
}
