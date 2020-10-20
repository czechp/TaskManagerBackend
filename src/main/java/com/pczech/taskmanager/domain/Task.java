package com.pczech.taskmanager.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
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


    //todo: Add @ManyToMany with app user

    @PrePersist()
    public void initEntity() {
        super.setTaskStatus(TaskStatus.TODO);
    }

    @PostLoad()
    public void postLoad() {
        //todo: implement recounting progress
    }
}
