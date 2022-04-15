package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.UserDto;

public interface UserManager {

    /**
     * Delegates user updating and user role checking (if role is DOCTOR) to related services.
     * @param user updated user data transfer object
     */
    void updateUserAndCheckRole(UserDto user);
}
