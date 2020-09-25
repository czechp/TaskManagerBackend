package com.pczech.taskmanager;

import com.pczech.taskmanager.domain.AppUser;
import com.pczech.taskmanager.domain.AppUserRole;
import com.pczech.taskmanager.domain.MaintenanceTask;
import com.pczech.taskmanager.domain.MaintenanceWorker;
import com.pczech.taskmanager.repository.AppUserRepository;
import com.pczech.taskmanager.repository.MaintenanceTaskRepository;
import com.pczech.taskmanager.repository.MaintenanceWorkerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component()
@Slf4j()
@Profile("development")
public class DevelopmentData {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final MaintenanceTaskRepository maintenanceTaskRepository;
    private final MaintenanceWorkerRepository maintenanceWorkerRepository;

    public DevelopmentData(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, MaintenanceTaskRepository maintenanceTaskRepository, MaintenanceWorkerRepository maintenanceWorkerRepository) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.maintenanceTaskRepository = maintenanceTaskRepository;
        this.maintenanceWorkerRepository = maintenanceWorkerRepository;
    }

    @Autowired()


    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.info("Development mode active");
        createUsers();
        createMaintenanceWorkers();
        createMaintenanceTask();
        log.info("End of development method");
    }


    private void createUsers() {
        //creating some users
        appUserRepository.save(
                AppUser.builder()
                        .username("user")
                        .password(passwordEncoder.encode("user"))
                        .email("user@gmail.com")
                        .role(AppUserRole.USER)
                        .adminApproved(true)
                        .tokenValidation(true)
                        .build()
        );

        appUserRepository.save(
                AppUser.builder()
                        .username("superuser")
                        .password(passwordEncoder.encode("superuser"))
                        .email("superuser@gmail.com")
                        .role(AppUserRole.SUPERUSER)
                        .adminApproved(true)
                        .tokenValidation(true)
                        .build()
        );
        appUserRepository.save(
                AppUser.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .email("admin@gmail.com")
                        .role(AppUserRole.ADMIN)
                        .adminApproved(true)
                        .tokenValidation(true)
                        .build()
        );
    }

    private void createMaintenanceWorkers() {
        maintenanceWorkerRepository.saveAndFlush(
                new MaintenanceWorker("Jacek", "Placek")
        );

        maintenanceWorkerRepository.save(
                new MaintenanceWorker("Piotr", "Adamczyk")
        );

        maintenanceWorkerRepository.save(
                new MaintenanceWorker("Michał", "Wisniewski")
        );

    }

    private void createMaintenanceTask() {
        MaintenanceWorker maintenanceWorker1 = maintenanceWorkerRepository.findById(1L).get();
        MaintenanceWorker maintenanceWorker2 = maintenanceWorkerRepository.findById(2L).get();
        MaintenanceWorker maintenanceWorker3 = maintenanceWorkerRepository.findById(3L).get();
        MaintenanceTask maintenanceTask = new MaintenanceTask();
        maintenanceTask.setMaintenanceWorker(maintenanceWorker1);
        maintenanceTask.setBreakdownPlace("Linia1");
        maintenanceTask.setDescription("description1");
        maintenanceTaskRepository.save(
                maintenanceTask
        );
    }
}
