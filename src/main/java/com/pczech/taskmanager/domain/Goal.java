package com.pczech.taskmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity(name = "goals")
@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@Builder()
public class Goal {
    @Id()
    @GeneratedValue()
    private long id;

    @NotNull()
    @NotEmpty()
    @Length(min = 5, max=30)
    private String content;

    @ManyToOne()
    @NotNull()
    private Task task;

}
