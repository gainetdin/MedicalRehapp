package com.telekom.javaschool.medicalrehapp.mapper;

import com.telekom.javaschool.medicalrehapp.dto.UserDto;
import com.telekom.javaschool.medicalrehapp.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity dtoToEntity(UserDto dto);

    UserDto entityToDto(UserEntity entity);

    List<UserEntity> dtoListToEntityList(List<UserDto> dtoList);

    List<UserDto> entityListToDtoList(List<UserEntity> entityList);
}
