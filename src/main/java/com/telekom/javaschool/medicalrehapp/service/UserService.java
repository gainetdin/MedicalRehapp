package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {

    void create(UserDto userDto);

    void update(UserDto userDto);

    UserDto findByLogin(String login);

    List<UserDto> findAll();
}
