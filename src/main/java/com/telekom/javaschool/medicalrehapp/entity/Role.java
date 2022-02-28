package com.telekom.javaschool.medicalrehapp.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    DOCTOR,
    NURSE,
    ADMIN,
    USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
