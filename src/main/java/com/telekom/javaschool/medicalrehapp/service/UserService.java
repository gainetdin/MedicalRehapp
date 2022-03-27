package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.UserDto;
import com.telekom.javaschool.medicalrehapp.entity.UserEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface UserService {

    void create(@Valid UserDto userDto);

    UserEntity update(@Valid UserDto userDto);

    UserDto findByLogin(String login);

    List<UserDto> findAll();
}
