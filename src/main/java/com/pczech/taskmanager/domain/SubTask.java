package com.pczech.taskmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Data()
@NoArgsConstructor()
@SuperBuilder()
@Entity(name = "subtasks")
public class SubTask extends TaskSuperClass {
    @Transient()
    private int progress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore()
    private Task task;

    @Override()
    public int hashCode(){
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.append(super.getTitle()).append(getId());
        return hashCodeBuilder.toHashCode();
    }

}
