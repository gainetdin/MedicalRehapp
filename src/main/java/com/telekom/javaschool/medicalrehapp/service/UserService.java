package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.UserDto;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface UserService {

    void create(@Valid UserDto userDto);

    void update(@Valid UserDto userDto);

    UserDto findByLogin(String login);

    List<UserDto> findAll();
}
