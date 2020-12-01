package com.pczech.taskmanager.service;

import com.pczech.taskmanager.exception.BadDataException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Service()
public class ValidatorService {
    private Validator validator;

    public ValidatorService() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public void validateWithException(Object object) {
        Set<ConstraintViolation<Object>> validate = validator.validate(object);
        if (!validate.isEmpty()) {
            String message = validate
                    .stream()
                    .map(error -> error.getMessage())
                    .reduce("", (accumulator, error) -> accumulator + error + ", ");
            throw new BadDataException(message);
        }
    }
}
