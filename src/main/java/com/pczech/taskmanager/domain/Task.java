package com.pczech.taskmanager.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity(name = "tasks")
@Data()
@NoArgsConstructor()
@SuperBuilder()
public class Task extends TaskSuperClass {

    @Range(max = 100, min = 0)
    @Transient()
    private int progress;

    @Enumerated(EnumType.STRING)
    @NotNull()
    private TaskPriority taskPriority;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Goal> goals = new LinkedHashSet<>();

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubTask> subTasks = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "tasks")
    private Set<AppUser> appUsers = new LinkedHashSet<>();

    @PrePersist()
    public void initEntity() {
        super.setTaskStatus(TaskStatus.TODO);
    }

    @PostLoad()
    public void postLoad() {
        recountProgress();
        specifyStatus();
    }

    @PreRemove()
    public void preRemove(){
        for (AppUser appUser : appUsers) {
            appUser.getTasks().remove(this);
            appUsers = new LinkedHashSet<>();
        }
    }


    public void addGoal(Goal goal) {
        this.goals.add(goal);
        goal.setTask(this);
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(super.getTitle());
        return hcb.toHashCode();
    }

    public void addSubTask(SubTask subTask) {
        this.subTasks.add(subTask);
        subTask.setTask(this);
    }

    private void specifyStatus() {
        if(super.getTaskStatus() == TaskStatus.IN_PROGRESS){
            super.setTaskStatus(progress == 100 ? TaskStatus.DONE : TaskStatus.IN_PROGRESS);
        }
    }


    private void recountProgress() {
        int allSubtasksNumber = subTasks.size();
        int doneSubTasksNumber = (int) subTasks.stream()
                .filter(x -> x.getTaskStatus() == TaskStatus.DONE)
                .count();
        if (allSubtasksNumber == doneSubTasksNumber && allSubtasksNumber != 0){
            progress = 100;
        }
        else if (allSubtasksNumber == 0)
            progress = 0;
        else
            progress = (doneSubTasksNumber * 100) / allSubtasksNumber;

    }

    public void addAppUser(AppUser appUser) {
        appUsers.add(appUser);
        appUser.getTasks().add(this);
    }
}
