package com.pczech.taskmanager.validator.implementation;

import com.pczech.taskmanager.domain.AppUserRole;
import com.pczech.taskmanager.domain.TaskComment;
import com.pczech.taskmanager.exception.UnauthorizedException;
import com.pczech.taskmanager.repository.CommentRepository;
import com.pczech.taskmanager.validator.annotation.CommentOwnerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.util.Optional;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class CommentOwnerValidatorImpl implements ConstraintValidator<CommentOwnerValidator, Object[]> {

    private CommentRepository commentRepository;

    @Autowired()
    public CommentOwnerValidatorImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public boolean isValid(Object[] objects, ConstraintValidatorContext constraintValidatorContext) {
        if(!isAdmin() && !isOwner((long) objects[0]))
            throw new UnauthorizedException("You are no owner of comment");
        return true;
    }


    private boolean isAdmin() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(
                        x -> x.equals("ROLE_" + AppUserRole.ADMIN)
                );
    }

    private boolean isOwner(long id) {
        Optional<TaskComment> optionalComment = commentRepository.findById(id);
        return optionalComment.isPresent() && optionalComment.get().getOwner().equals(getUsername());
    }

    private String getUsername() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }
}
