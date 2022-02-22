package com.telekom.javaschool.medicalrehapp.dto;

import com.telekom.javaschool.medicalrehapp.entity.TreatmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentDto implements Serializable {
    private String name;
    private TreatmentType type;
}
