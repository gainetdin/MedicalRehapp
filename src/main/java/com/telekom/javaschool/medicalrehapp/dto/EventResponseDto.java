package com.telekom.javaschool.medicalrehapp.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EventResponseDto {

    private List<EventDto> data;

    private int draw;

    private int recordsTotal;

    private int recordsFiltered;
}
