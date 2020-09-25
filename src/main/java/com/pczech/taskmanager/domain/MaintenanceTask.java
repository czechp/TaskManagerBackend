package com.pczech.taskmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity(name = "maintenance_tasks")
@Data()
@NoArgsConstructor()
@AllArgsConstructor()
public class MaintenanceTask extends TaskSuperClass {
    @ManyToOne()
    @NotNull()
    private MaintenanceWorker maintenanceWorker;

    @ManyToOne()
    private AppUser repairMan;

    @NotNull()
    @Length(min = 3, max = 50)
    private String breakdownPlace;

    private String repairConclusion;

    @Override
    public String toString() {
        return "MaintenanceTask{" +
                "breakdownPlace='" + breakdownPlace + '\'' +
                ", repairConclusion='" + repairConclusion + '\'' +
                '}';
    }
}
