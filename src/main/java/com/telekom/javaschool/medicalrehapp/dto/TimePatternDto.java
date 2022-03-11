package com.telekom.javaschool.medicalrehapp.dto;

import com.telekom.javaschool.medicalrehapp.entity.TimeBasis;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimePatternDto extends AbstractDto implements Serializable {
    private TimeBasis timeBasis;
    private int dailyFrequency;
    private List<TimePatternElementDto> timePatternElement;
}
