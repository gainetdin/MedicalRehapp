package com.telekom.javaschool.medicalrehapp.mapper;

import com.telekom.javaschool.medicalrehapp.dto.DoctorDto;
import com.telekom.javaschool.medicalrehapp.entity.DoctorEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class DoctorEntityMapperTest {

    private final DoctorMapper mapper = Mappers.getMapper(DoctorMapper.class);

    @Test
    void shouldMapEntityToDto() {
        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setName("Gregory House");

        DoctorDto doctorDto = mapper.entityToDto(doctorEntity);

        Assertions.assertEquals(doctorEntity.getName(), doctorDto.getName());
    }

    @Test
    void shouldMapDtoToEntity() {
        DoctorDto doctorDto = new DoctorDto();
        doctorDto.setName("James Wilson");

        DoctorEntity doctorEntity = mapper.dtoToEntity(doctorDto);

        Assertions.assertEquals(doctorDto.getName(), doctorEntity.getName());
    }
}