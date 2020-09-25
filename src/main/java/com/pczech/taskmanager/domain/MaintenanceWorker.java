package com.pczech.taskmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "maintenanceWorker", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore()
    private List<MaintenanceTask> maintenanceTasks = new ArrayList<>();

    public MaintenanceWorker(@NotNull() @NotBlank() @Length(min = 3, max = 20) String firstName, @NotNull() @NotBlank() @Length(min = 3, max = 20) String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    @Override
    public String toString() {
        return "MaintenanceWorker{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                '}';
    }
}
