package com.telekom.javaschool.medicalrehapp.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumValueValidator implements ConstraintValidator<EnumValue, CharSequence> {

    private List<String> permittedValues;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        permittedValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(CharSequence charSequence,
                           ConstraintValidatorContext constraintValidatorContext) {
        return permittedValues.contains(charSequence.toString());
    }
}
