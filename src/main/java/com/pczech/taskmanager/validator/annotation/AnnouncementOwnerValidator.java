package com.pczech.taskmanager.validator.annotation;

import com.pczech.taskmanager.validator.implementation.AnnouncementOwnerValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = AnnouncementOwnerValidatorImpl.class)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@Documented()
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnouncementOwnerValidator {
    String message() default
            "You aren't owner of the announcement";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
