package com.telekom.javaschool.medicalrehapp.mapper;

import com.telekom.javaschool.medicalrehapp.dto.TimePatternElementDto;
import com.telekom.javaschool.medicalrehapp.entity.TimePatternElement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TimePatternElementMapper {

    TimePatternElement dtoToEntity(TimePatternElementDto dto);

    TimePatternElementDto entityToDto(TimePatternElement entity);
}
