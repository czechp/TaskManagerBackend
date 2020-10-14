package com.pczech.taskmanager.service;

import com.pczech.taskmanager.domain.AppUser;
import com.pczech.taskmanager.domain.AppUserRole;
import com.pczech.taskmanager.domain.MaintenanceTask;
import com.pczech.taskmanager.domain.MaintenanceWorker;
import com.pczech.taskmanager.repository.MaintenanceTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

@SpringBootTest()
class MaintenanceTaskServiceImplTest {
    @MockBean()
    MaintenanceTaskRepository maintenanceTaskRepository;
    private MaintenanceTaskService maintenanceTaskService;
    private MaintenanceTask maintenanceTask;
    private MaintenanceWorker maintenanceWorker;
    private AppUser appUser;

    @BeforeEach()
    public void init() {
        this.maintenanceTaskService = new MaintenanceTaskServiceImpl(maintenanceTaskRepository);
        this.maintenanceWorker = MaintenanceWorker.builder()
                .id(1L)
                .firstName("Adam")
                .secondName("Szajer")
                .maintenanceTasks(new ArrayList<>())
                .breakdownsAmount(0)
                .build();
        this.appUser = AppUser.builder()
                .username("admin")
                .password(("admin"))
                .email("webcoderc@gmail.com")
                .role(AppUserRole.ADMIN)
                .adminApproved(true)
                .tokenValidation(true)
                .build();

        this.maintenanceTask = MaintenanceTask.builder()
                .maintenanceWorker(maintenanceWorker)
                .repairMan(appUser)
                .breakdownPlace("Hala A")
                .breakdownMachine("Famag")
                .repairConclusion("Uszkodzony czujnik")
                .build();

    }
}