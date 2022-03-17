package com.telekom.javaschool.medicalrehapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.DayOfWeek;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimePatternElementDto extends AbstractDto implements Serializable {
    private DayOfWeek dayOfWeek;
}
