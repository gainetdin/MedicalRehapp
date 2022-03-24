package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.constant.LogMessages;
import com.telekom.javaschool.medicalrehapp.dao.DoctorRepository;
import com.telekom.javaschool.medicalrehapp.dao.UserRepository;
import com.telekom.javaschool.medicalrehapp.dto.UserDto;
import com.telekom.javaschool.medicalrehapp.entity.DoctorEntity;
import com.telekom.javaschool.medicalrehapp.entity.Role;
import com.telekom.javaschool.medicalrehapp.entity.UserEntity;
import com.telekom.javaschool.medicalrehapp.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           DoctorRepository doctorRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void create(UserDto user) {
        String login = user.getLogin();
        if (userRepository.findByLogin(login).isPresent()) {
            throw new EntityExistsException(String.format(LogMessages.USER_EXISTS, login));
        }
        UserDto userDto = UserDto.builder()
                .login(user.getLogin())
                .name(user.getName())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(Role.USER)
                .build();
        log.debug(userDto.toString());
        userRepository.save(userMapper.dtoToEntity(userDto));
    }

    @Override
    @Transactional
    public void update(UserDto user) {
        UserEntity userEntity = getUserEntityByLogin(user.getLogin());
        userEntity.setName(user.getName());
        Role role = user.getRole();
        userEntity.setRole(user.getRole());
        UserEntity savedUserEntity = userRepository.save(userEntity);
        if (role == Role.DOCTOR) {
            DoctorEntity doctorEntity = new DoctorEntity();
            doctorEntity.setUser(savedUserEntity);
            doctorRepository.save(doctorEntity);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findByLogin(String login) {
        return userMapper.entityToDto(getUserEntityByLogin(login));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return userMapper.entityListToDtoList(userRepository.findAll());
    }

    @Override
    public List<UserDto> findAllDoctors() {
        return userMapper.entityListToDtoList(userRepository.findAllByRole(Role.DOCTOR));
    }

    private UserEntity getUserEntityByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(String.format(LogMessages.USER_NOT_FOUND, login)));
    }
}
