package com.pczech.taskmanager.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity(name = "maintenance_workers")
public class MaintenanceWorker {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull()
    @NotBlank()
    @Length(min = 3, max = 20)
    private String firstName;


    @NotNull()
    @NotBlank()
    @Length(min = 3, max = 20)
    private String secondName;
}
