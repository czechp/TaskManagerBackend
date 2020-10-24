package com.pczech.taskmanager.validator.implementation;

import com.pczech.taskmanager.domain.AppUserRole;
import com.pczech.taskmanager.validator.annotation.TaskOwnerValidator;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class TaskOwnerValidatorImpl implements ConstraintValidator<TaskOwnerValidator, Object[]> {

    @Override
    public boolean isValid(Object[] objects, ConstraintValidatorContext constraintValidatorContext) {
        return isAdmin();
    }

    private boolean isAdmin() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(x ->
                        x.toString().equals("ROLE_" + AppUserRole.ADMIN.toString())
                                || x.toString().equals("ROLE_" + AppUserRole.SUPERUSER.toString())
                );
    }
}
