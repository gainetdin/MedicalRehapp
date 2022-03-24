package com.telekom.javaschool.medicalrehapp.dto;

import com.telekom.javaschool.medicalrehapp.entity.TreatmentType;
import com.telekom.javaschool.medicalrehapp.validator.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentDto extends AbstractDto implements Serializable {

    @NotBlank
    private String name;

    @EnumValue(enumClass = TreatmentType.class)
    private TreatmentType type;
}
