package com.pczech.taskmanager.validator.annotation;

import com.pczech.taskmanager.validator.implementation.AnnouncementCommentOwnerValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = AnnouncementCommentOwnerValidatorImpl.class)
@Documented()
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AnnouncementCommentOwnerValidator {
    String message() default
            "You aren't owner of the announcement comment";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
