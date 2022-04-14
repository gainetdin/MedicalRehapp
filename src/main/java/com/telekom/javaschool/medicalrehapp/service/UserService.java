package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dto.UserDto;
import com.telekom.javaschool.medicalrehapp.entity.UserEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface UserService {

    /**
     * Creates new user with USER role.
     * @param userDto user data transfer object
     */
    void create(@Valid UserDto userDto);

    /**
     * Updates user.
     * @param userDto user data transfer object
     * @return saved user entity
     */
    UserEntity update(@Valid UserDto userDto);

    /**
     * Gets user by his login.
     * @param login unique login of user
     * @return user data transfer object
     */
    UserDto findByLogin(String login);

    /**
     * Gets all users from repository.
     * @return list of users
     */
    List<UserDto> findAll();
}
