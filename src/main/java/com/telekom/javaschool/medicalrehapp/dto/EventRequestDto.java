package com.telekom.javaschool.medicalrehapp.dto;

import com.telekom.javaschool.medicalrehapp.entity.EventStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EventRequestDto {

    private int draw;

    private int start;

    private int length;

    private String startDateTime;

    private String endDateTime;

    private List<EventStatus> statuses;

    private String search;
}
