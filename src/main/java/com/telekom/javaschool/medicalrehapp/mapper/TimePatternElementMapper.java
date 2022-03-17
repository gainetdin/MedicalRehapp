package com.telekom.javaschool.medicalrehapp.mapper;

import com.telekom.javaschool.medicalrehapp.dto.TimePatternElementDto;
import com.telekom.javaschool.medicalrehapp.entity.TimePatternElementEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TimePatternElementMapper {

    TimePatternElementEntity dtoToEntity(TimePatternElementDto dto);

    TimePatternElementDto entityToDto(TimePatternElementEntity entity);

    List<TimePatternElementEntity> dtoListToEntityList(List<TimePatternElementDto> dtoList);

    List<TimePatternElementDto> entityListToDtoList(List<TimePatternElementEntity> entityList);

//    default List<TimePatternElementDto> entityListToDtoList(List<TimePatternElementEntity> entityList) {
//        return entityList.stream()
//                .map(this::entityToDto)
//                .peek(timePatternElementDto -> timePatternElementDto.setTimePattern(null))
//                .collect(Collectors.toList());
//    }
}
