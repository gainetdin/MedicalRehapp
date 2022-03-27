package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.UserDto;
import com.telekom.javaschool.medicalrehapp.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserManagerImpl implements UserManager {

    private final UserService userService;
    private final DoctorService doctorService;

    @Autowired
    public UserManagerImpl(UserService userService, DoctorService doctorService) {
        this.userService = userService;
        this.doctorService = doctorService;
    }

    @Override
    @Transactional
    public void updateUserAndCheckRole(UserDto user) {
        UserDto previousUser = userService.findByLogin(user.getLogin());
        UserEntity currentUser = userService.update(user);
        doctorService.checkDoctorList(previousUser, currentUser);
    }
}
