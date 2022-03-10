package com.telekom.javaschool.medicalrehapp.mapper;

import com.telekom.javaschool.medicalrehapp.entity.User;
import com.telekom.javaschool.medicalrehapp.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User dtoToEntity(UserDto dto);

    UserDto entityToDto(User entity);
}
