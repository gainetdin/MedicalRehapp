package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.UserDto;

public interface UserManager {

    void updateUserAndCheckRole(UserDto user);
}
