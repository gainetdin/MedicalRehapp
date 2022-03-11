package com.telekom.javaschool.medicalrehapp.mapper;

import com.telekom.javaschool.medicalrehapp.dto.PatientDto;
import com.telekom.javaschool.medicalrehapp.entity.PatientEntity;
import com.telekom.javaschool.medicalrehapp.entity.UserEntity;
import com.telekom.javaschool.medicalrehapp.dto.UserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity dtoToEntity(UserDto dto);

    UserDto entityToDto(UserEntity entity);

    List<UserEntity> dtoListToEntityList(List<UserDto> dtoList);

    List<UserDto> entityListToDtoList(List<UserEntity> entityList);
}
