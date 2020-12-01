package com.pczech.taskmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@MappedSuperclass()
@NoArgsConstructor()
@AllArgsConstructor()
@SuperBuilder()
@Data()
public class CommentSuperClass {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Owner cannot be null")
    @NotBlank(message = "Owner cannot be blank")
    private String owner;

    @NotNull(message = "Content cannot be null")
    @NotBlank(message = "Content cannot be blank")
    private String fullName;

    @NotNull(message = "Content cannot be null")
    @NotBlank(message = "Content cannot be blank")
    private String content;


    @CreationTimestamp()
    private LocalDateTime creationDate;

}
