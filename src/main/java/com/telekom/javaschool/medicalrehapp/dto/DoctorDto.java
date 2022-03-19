package com.telekom.javaschool.medicalrehapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto extends AbstractDto implements Serializable {
    private UserDto user;
}
