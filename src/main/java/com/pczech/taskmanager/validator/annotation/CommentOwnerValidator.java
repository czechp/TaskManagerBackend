package com.pczech.taskmanager.validator.annotation;

import com.pczech.taskmanager.validator.implementation.CommentOwnerValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = CommentOwnerValidatorImpl.class)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@Documented()
@Retention(RetentionPolicy.RUNTIME)
public @interface CommentOwnerValidator {
    String message() default
            "You aren't owner of the comment";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
