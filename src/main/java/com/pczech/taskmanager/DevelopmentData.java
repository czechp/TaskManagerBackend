package com.pczech.taskmanager;

import com.pczech.taskmanager.domain.*;
import com.pczech.taskmanager.repository.AppUserRepository;
import com.pczech.taskmanager.repository.MaintenanceTaskRepository;
import com.pczech.taskmanager.repository.MaintenanceWorkerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.config.Task;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component()
@Slf4j()
@Profile("development")
public class DevelopmentData {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final MaintenanceTaskRepository maintenanceTaskRepository;
    private final MaintenanceWorkerRepository maintenanceWorkerRepository;
    @Autowired()
    public DevelopmentData(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, MaintenanceTaskRepository maintenanceTaskRepository, MaintenanceWorkerRepository maintenanceWorkerRepository) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.maintenanceTaskRepository = maintenanceTaskRepository;
        this.maintenanceWorkerRepository = maintenanceWorkerRepository;
    }



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
                        .email("webcoderc@gmail.com")
                        .role(AppUserRole.ADMIN)
                        .adminApproved(true)
                        .tokenValidation(true)
                        .build()
        );
    }

    private void createMaintenanceWorkers() {
        maintenanceWorkerRepository.save(
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
        //task todo
        MaintenanceTask maintenanceTaskToDo = new MaintenanceTask();
        maintenanceTaskToDo.setTitle("Awaria 1");
        maintenanceTaskToDo.setMaintenanceWorker(maintenanceWorker1);
        maintenanceTaskToDo.setBreakdownPlace("Linia1");
        maintenanceTaskToDo.setBreakdownMachine("FlowPack");
        maintenanceTaskToDo.setDescription("description1");
        maintenanceTaskToDo.setRepairConclusion("Jakies podsumowanie1");
        maintenanceTaskToDo.setTaskStatus(TaskStatus.TODO);
        maintenanceTaskToDo.setRepairMan(appUserRepository.findByUsername("user").get());
        maintenanceTaskRepository.save(
                maintenanceTaskToDo
        );
        //task in progress
        MaintenanceTask maintenanceTaskInProgress = new MaintenanceTask();
        maintenanceTaskInProgress.setTitle("Awaria 2");
        maintenanceTaskInProgress.setMaintenanceWorker(maintenanceWorker2);
        maintenanceTaskInProgress.setBreakdownPlace("Linia2");
        maintenanceTaskInProgress.setBreakdownMachine("L-23");
        maintenanceTaskInProgress.setDescription("description2");
        maintenanceTaskToDo.setRepairConclusion("Jakies podsumowanie2");
        maintenanceTaskInProgress.setTaskStatus(TaskStatus.IN_PROGRESS);
        maintenanceTaskInProgress.setRepairMan(appUserRepository.findByUsername("superuser").get());
        maintenanceTaskRepository.save(
                maintenanceTaskInProgress
        );

        //task in progress
        MaintenanceTask maintenanceTaskDone = new MaintenanceTask();
        maintenanceTaskDone.setTitle("Awaria 3");
        maintenanceTaskDone.setMaintenanceWorker(maintenanceWorker3);
        maintenanceTaskDone.setBreakdownPlace("Linia3");
        maintenanceTaskDone.setBreakdownMachine("Paczkarka");
        maintenanceTaskDone.setDescription("description3");
        maintenanceTaskToDo.setRepairConclusion("Jakies podsumowanie3");
        maintenanceTaskDone.setTaskStatus(TaskStatus.DONE);
        maintenanceTaskDone.setRepairMan(appUserRepository.findByUsername("admin").get());
        maintenanceTaskDone.setRepairConclusion("Any conslusion");
        maintenanceTaskDone.setFinishDate(LocalDateTime.now());
        maintenanceTaskRepository.save(
                maintenanceTaskDone
        );
    }
}
