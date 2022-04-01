package com.telekom.javaschool.medicalrehapp.dto;

import com.telekom.javaschool.medicalrehapp.entity.Role;
import com.telekom.javaschool.medicalrehapp.validator.CreateMarker;
import com.telekom.javaschool.medicalrehapp.validator.EnumValue;
import com.telekom.javaschool.medicalrehapp.validator.UpdateMarker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends AbstractDto implements Serializable {

    @NotBlank(message = "Login cannot be blank", groups = CreateMarker.class)
    private String login;

    @NotBlank(message = "Login cannot be blank", groups = CreateMarker.class)
    private String password;

    @NotBlank(message = "Login cannot be blank")
    private String name;

    @EnumValue(enumClass = Role.class, groups = UpdateMarker.class)
    private Role role;
}
