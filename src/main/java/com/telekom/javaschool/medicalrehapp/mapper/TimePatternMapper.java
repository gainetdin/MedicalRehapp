package com.telekom.javaschool.medicalrehapp.mapper;

import com.telekom.javaschool.medicalrehapp.dto.TimePatternDto;
import com.telekom.javaschool.medicalrehapp.entity.TimePatternEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TimePatternMapper {

    TimePatternEntity dtoToEntity(TimePatternDto dto);

    TimePatternDto entityToDto(TimePatternEntity entity);

    List<TimePatternEntity> dtoListToEntityList(List<TimePatternDto> dtoList);

    List<TimePatternDto> entityListToDtoList(List<TimePatternEntity> entityList);
}
