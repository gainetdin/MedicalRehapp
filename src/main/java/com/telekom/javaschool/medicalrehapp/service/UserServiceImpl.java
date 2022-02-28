package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dao.UserRepository;
import com.telekom.javaschool.medicalrehapp.dto.UserDto;
import com.telekom.javaschool.medicalrehapp.entity.Role;
import com.telekom.javaschool.medicalrehapp.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void create(UserDto user) {
        if (userRepository.findByLogin(user.getLogin()) != null) {
            log.debug("User with this login already exists");
            return;
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
        UserDto userDto = userMapper.entityToDto(userRepository.findByLogin(user.getLogin()));
        userDto.setName(user.getName());
        userDto.setRole(user.getRole());
        log.debug(userDto.toString());
        log.debug("Id of user: {}", userDto.getId());
        userRepository.save(userMapper.dtoToEntity(userDto));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findByLogin(String login) {
        return userMapper.entityToDto(userRepository.findByLogin(login));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByLogin(username);
    }
}
