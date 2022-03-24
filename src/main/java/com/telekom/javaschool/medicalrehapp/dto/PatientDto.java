package com.telekom.javaschool.medicalrehapp.dto;

import com.telekom.javaschool.medicalrehapp.entity.PatientStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto extends AbstractDto implements Serializable {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Diagnosis cannot be blank")
    private String diagnosis;

    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{3}-\\d{2}$", message = "Insurance number should be in XXX-XXX-XXX-XX format")
    private String insuranceNumber;

    private DoctorDto doctor;

    private PatientStatus patientStatus;
}
