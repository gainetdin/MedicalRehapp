package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dao.UserRepository;
import com.telekom.javaschool.medicalrehapp.dto.UserDto;
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
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void create(UserDto user) {
        if (userRepository.findByLogin(user.getLogin()).isPresent()) {
            throw new EntityExistsException("User with this login already exists");
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
        userEntity.setRole(user.getRole());
        userRepository.save(userEntity);
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

    private UserEntity getUserEntityByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("User with this login doesn't exist"));
    }
}
