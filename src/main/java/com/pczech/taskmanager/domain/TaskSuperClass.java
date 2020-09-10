package com.pczech.taskmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@Entity()
public class TaskSuperClass {
    @ManyToMany(mappedBy = "tasks", fetch = FetchType.EAGER)
    List<AppUser> executors = new ArrayList<>();
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    @ManyToOne()
    private AppUser owner;
    private LocalDateTime creationDate;

    public TaskSuperClass(String description, AppUser owner) {
        this.description = description;
        this.owner = owner;
    }
}
