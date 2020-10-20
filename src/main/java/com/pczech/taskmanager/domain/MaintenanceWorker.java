package com.pczech.taskmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder()
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

    @OneToMany(mappedBy = "maintenanceWorker", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore()
    private List<MaintenanceTask> maintenanceTasks = new ArrayList<>();

    @Transient()
    private int breakdownsAmount;

    public MaintenanceWorker(@NotNull() @NotBlank() @Length(min = 3, max = 20) String firstName, @NotNull() @NotBlank() @Length(min = 3, max = 20) String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    @PreRemove()
    public void preRemove() {
        maintenanceTasks.stream().forEach(x -> x.setMaintenanceWorker(null));
        maintenanceTasks = null;
    }

    @PostLoad()
    public void recountBreakdowns() {
        breakdownsAmount = maintenanceTasks.size();
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
