package com.telekom.javaschool.medicalrehapp.dto;

import com.telekom.javaschool.medicalrehapp.entity.TimeBasis;
import com.telekom.javaschool.medicalrehapp.validator.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimePatternDto extends AbstractDto implements Serializable {

    @EnumValue(enumClass = TimeBasis.class)
    private TimeBasis timeBasis;

    @Min(1)
    @Max(4)
    private int dailyFrequency;

    private List<TimePatternElementDto> timePatternElement;
}
