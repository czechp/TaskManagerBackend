package com.pczech.taskmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity(name = "maintenance_workers")
@Data()
@NoArgsConstructor()
@AllArgsConstructor()
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

    public MaintenanceWorker(@NotNull() @NotBlank() @Length(min = 3, max = 20) String firstName, @NotNull() @NotBlank() @Length(min = 3, max = 20) String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }
}
