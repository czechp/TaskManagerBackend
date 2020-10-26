package com.pczech.taskmanager.validator.implementation;

import com.pczech.taskmanager.domain.AppUserRole;
import com.pczech.taskmanager.domain.Task;
import com.pczech.taskmanager.repository.TaskRepository;
import com.pczech.taskmanager.validator.annotation.TaskOwnerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.util.Optional;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class TaskOwnerValidatorImpl implements ConstraintValidator<TaskOwnerValidator, Object[]> {

    private TaskRepository taskRepository;

    @Autowired()
    public TaskOwnerValidatorImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @Override
    public boolean isValid(Object[] objects, ConstraintValidatorContext constraintValidatorContext) {
        return isAdminOrSuperUser() || isOwner((Long) objects[0]);
    }

    private boolean isOwner(long taskId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Task> optionalTask = taskRepository.findById((long) taskId);
        if(optionalTask.isPresent()){
            return optionalTask.get().getAppUsers()
                    .stream()
                    .anyMatch(x->x.getUsername().equals(username));
        }
        return true;
    }

    private boolean isAdminOrSuperUser() {
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
