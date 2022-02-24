package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.UserDto;

public interface UserService {

    void save(UserDto userDto);

    UserDto findByUsername(String username);
}
