package com.pczech.taskmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity(name = "maintenance_tasks")
@Data()
@NoArgsConstructor()
@AllArgsConstructor()
public class MaintenanceTask extends TaskSuperClass {
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull()
    private MaintenanceWorker maintenanceWorker;

    @NotNull()
    @Length(min = 3, max = 50)
    private String breakdownPlace;

    private String repairConclusion;

}
