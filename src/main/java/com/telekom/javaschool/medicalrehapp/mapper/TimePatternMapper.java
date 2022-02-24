package com.telekom.javaschool.medicalrehapp.mapper;

import com.telekom.javaschool.medicalrehapp.dto.TimePatternDto;
import com.telekom.javaschool.medicalrehapp.entity.TimePattern;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TimePatternMapper {

    TimePattern dtoToEntity(TimePatternDto dto);

    TimePatternDto entityToDto(TimePattern entity);
}
