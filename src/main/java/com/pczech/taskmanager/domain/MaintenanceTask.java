package com.pczech.taskmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity(name = "maintenance_tasks")
@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@SuperBuilder()
public class MaintenanceTask extends TaskSuperClass {
    @ManyToOne()
    private MaintenanceWorker maintenanceWorker;

    @ManyToOne()
    private AppUser repairMan;

    @NotNull()
    @Length(min = 1, max = 50)
    private String breakdownPlace;

    @NotNull()
    @Length(min = 3, max = 50)
    private String breakdownMachine;

    private String repairConclusion;

    @PreRemove()
    public void preRemove() {
        this.maintenanceWorker.getMaintenanceTasks().remove(this);
        this.maintenanceWorker = null;
        this.repairMan.getMaintenanceTasks().remove(this);
        this.repairMan = null;
    }

    @Override
    public String toString() {
        return "MaintenanceTask{" +
                "breakdownPlace='" + breakdownPlace + '\'' +
                ", repairConclusion='" + repairConclusion + '\'' +
                '}';
    }

    @PreUpdate()
    public void setFinishDate(){
        if(super.getTaskStatus() == TaskStatus.DONE && super.getFinishDate() == null)
            super.setFinishDate(LocalDateTime.now());
    }
}
