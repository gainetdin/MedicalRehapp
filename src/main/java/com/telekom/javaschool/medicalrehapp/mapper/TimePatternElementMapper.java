package com.telekom.javaschool.medicalrehapp.mapper;

import com.telekom.javaschool.medicalrehapp.dto.PatientDto;
import com.telekom.javaschool.medicalrehapp.dto.TimePatternElementDto;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import com.telekom.javaschool.medicalrehapp.entity.TimePatternElementEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TimePatternElementMapper {

    TimePatternElementEntity dtoToEntity(TimePatternElementDto dto);

    TimePatternElementDto entityToDto(TimePatternElementEntity entity);

    List<TimePatternElementEntity> dtoListToEntityList(List<TimePatternElementDto> dtoList);

    List<TimePatternElementDto> entityListToDtoList(List<TimePatternElementEntity> entityList);
}
