package com.pczech.taskmanager.validator.annotation;

import com.pczech.taskmanager.validator.implementation.TaskOwnerValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = TaskOwnerValidatorImpl.class)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@Documented()
@Retention(RetentionPolicy.RUNTIME)
public @interface TaskOwnerValidator {
    String message() default
            "You aren't owner of the task";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
