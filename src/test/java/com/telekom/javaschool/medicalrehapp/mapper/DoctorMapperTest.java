package com.telekom.javaschool.medicalrehapp.mapper;

import com.telekom.javaschool.medicalrehapp.dto.DoctorDto;
import com.telekom.javaschool.medicalrehapp.entity.Doctor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class DoctorMapperTest {

    private final DoctorMapper mapper = Mappers.getMapper(DoctorMapper.class);

    @Test
    void shouldMapEntityToDto() {
        Doctor doctor = new Doctor();
        doctor.setName("Gregory House");

        DoctorDto doctorDto = mapper.entityToDto(doctor);

        Assertions.assertEquals(doctor.getName(), doctorDto.getName());
    }

    @Test
    void shouldMapDtoToEntity() {
        DoctorDto doctorDto = new DoctorDto();
        doctorDto.setName("James Wilson");

        Doctor doctor = mapper.dtoToEntity(doctorDto);

        Assertions.assertEquals(doctorDto.getName(), doctor.getName());
    }
}