package com.telekom.javaschool.medicalrehapp.validator;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class EnumValueValidator implements ConstraintValidator<EnumValue, Enum<?>> {

    private List<Enum<?>> permittedValues;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        permittedValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(Enum<?> currentValue,
                           ConstraintValidatorContext constraintValidatorContext) {
        return permittedValues.contains(currentValue);
    }
}
