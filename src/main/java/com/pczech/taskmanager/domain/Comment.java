package com.pczech.taskmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity(name = "comments")
@Data()
@Builder()
@NoArgsConstructor()
@AllArgsConstructor()
public class Comment {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Owner cannot be null")
    @NotBlank(message = "Owner cannot be blank")
    private String owner;

    @NotNull(message = "Content cannot be null")
    @NotBlank(message = "Content cannot be blank")
    private String content;

    @CreationTimestamp()
    private LocalDateTime creationDate;

    @JsonIgnore()
    @ManyToOne(fetch = FetchType.LAZY)
    private Task task;

    @Override()
    public int hashCode(){
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.append(id);
        hashCodeBuilder.append(owner);
        hashCodeBuilder.append(content);
        return hashCodeBuilder.toHashCode();
    }
}
