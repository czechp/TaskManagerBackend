package com.pczech.taskmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@MappedSuperclass()
public class TaskSuperClass {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime creationDate;

    private LocalDateTime finishDate;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

}
