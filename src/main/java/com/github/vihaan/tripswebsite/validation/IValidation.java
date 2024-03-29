package com.github.vihaan.tripswebsite.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface IValidation<T> {

    List<String> isValid(T objectToValidate);

    default List<String> validatorCheck(T objectToValidate) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> validationErrors = validator.validate(objectToValidate);
        return validationErrors.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
    }

    String DATES_CONFLICT = "End date have to be after start date.";
    String DB_CONFLICT = "This object already exists in database.";
}
