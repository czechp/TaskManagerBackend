package com.pczech.taskmanager.validator.implementation;

import com.pczech.taskmanager.repository.AnnouncementRepository;
import com.pczech.taskmanager.service.AppUserService;
import com.pczech.taskmanager.validator.annotation.AnnouncementOwnerValidator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class AnnouncementOwnerValidatorImpl implements ConstraintValidator<AnnouncementOwnerValidator, Object[]> {
    private AnnouncementRepository announcementRepository;
    private AppUserService appUserService;

    @Autowired()
    public AnnouncementOwnerValidatorImpl(AnnouncementRepository announcementRepository, AppUserService appUserService) {
        this.announcementRepository = announcementRepository;
        this.appUserService = appUserService;
    }

    @Override
    public boolean isValid(Object[] objects, ConstraintValidatorContext constraintValidatorContext) {
        boolean isAdmin = appUserService.getCurrentUserRoles()
                .stream()
                .anyMatch(role -> role.equals("ADMIN"));
        boolean isOwner = announcementRepository.existsByIdAndAppUser((long) objects[0], appUserService.getCurrentUser());
        return isAdmin || isOwner;
    }
}
