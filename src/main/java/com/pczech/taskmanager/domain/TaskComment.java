package com.pczech.taskmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity(name = "comments")
@Data()
@SuperBuilder()
@NoArgsConstructor()
@AllArgsConstructor()
public class TaskComment extends CommentSuperClass {


    @JsonIgnore()
    @ManyToOne(fetch = FetchType.LAZY)
    private Task task;

    @Override()
    public int hashCode(){
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.append(super.getId());
        hashCodeBuilder.append(super.getOwner());
        hashCodeBuilder.append(super.getContent());
        return hashCodeBuilder.toHashCode();
    }
}
