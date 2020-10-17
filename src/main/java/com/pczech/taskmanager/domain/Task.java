package com.pczech.taskmanager.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "tasks")
@Data()
@NoArgsConstructor()
@SuperBuilder()
public class Task extends TaskSuperClass {

    @Range(max = 100, min = 0)
    private int progress;

    @Enumerated(EnumType.STRING)
    private TaskPriority taskPriority=TaskPriority.LOW;

    @OneToMany(mappedBy = "task", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Goal> goals = new HashSet<>();

    //todo: Add @ManyToMany with app user

    @PostLoad()
    public void postLoad(){
        //todo: implement recounting progress
    }
}
