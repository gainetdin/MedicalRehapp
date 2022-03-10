package com.telekom.javaschool.medicalrehapp.mapper;

import com.telekom.javaschool.medicalrehapp.dto.DoctorDto;
import com.telekom.javaschool.medicalrehapp.entity.DoctorEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    DoctorEntity dtoToEntity(DoctorDto dto);

    DoctorDto entityToDto(DoctorEntity entity);

    List<DoctorEntity> dtoListToEntityList(List<DoctorDto> dtoList);

    List<DoctorDto> entityListToDtoList(List<DoctorEntity> entityList);
}
