package com.pczech.taskmanager.validator.implementation;

import com.pczech.taskmanager.repository.AnnouncementCommentRepository;
import com.pczech.taskmanager.service.AppUserService;
import com.pczech.taskmanager.validator.annotation.AnnouncementCommentOwnerValidator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class AnnouncementCommentOwnerValidatorImpl implements ConstraintValidator<AnnouncementCommentOwnerValidator, Object[]> {
    private final AnnouncementCommentRepository announcementCommentRepository;
    private final AppUserService appUserService;

    @Autowired()
    public AnnouncementCommentOwnerValidatorImpl(AnnouncementCommentRepository announcementCommentRepository, AppUserService appUserService) {
        this.announcementCommentRepository = announcementCommentRepository;
        this.appUserService = appUserService;
    }

    @Override
    public boolean isValid(Object[] objects, ConstraintValidatorContext constraintValidatorContext) {
        boolean isAdmin = appUserService.getCurrentUserRoles()
                .stream()
                .anyMatch(role -> role.equals("ADMIN"));
        boolean isOwner = announcementCommentRepository.existsByIdAndOwner((long) objects[0], appUserService.getCurrentUser().getUsername());
        return isAdmin || isOwner;
    }
}
