package com.pczech.taskmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@MappedSuperclass()
@SuperBuilder()
public class TaskSuperClass {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreationTimestamp()
    private LocalDateTime creationDate;

    private LocalDateTime finishDate;

    @Length(min = 3, max = 40)
    @NotNull()
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus = TaskStatus.TODO;

    @UpdateTimestamp()
    private LocalDateTime updateDate;

}
