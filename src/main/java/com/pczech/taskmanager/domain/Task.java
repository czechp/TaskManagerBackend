package com.pczech.taskmanager.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

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

    //todo: Add @ManyToMany with app user

    @PrePersist()
    public void initEntity() {
        super.setTaskStatus(TaskStatus.TODO);
    }

    @PostLoad()
    public void postLoad() {
        //todo: implement recounting progress
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
}
