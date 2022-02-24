package com.telekom.javaschool.medicalrehapp.mapper;

import com.telekom.javaschool.medicalrehapp.dto.DoctorDto;
import com.telekom.javaschool.medicalrehapp.entity.Doctor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    Doctor dtoToEntity(DoctorDto dto);

    DoctorDto entityToDto(Doctor entity);
}
