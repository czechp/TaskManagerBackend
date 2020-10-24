package com.pczech.taskmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Data()
@NoArgsConstructor()
@SuperBuilder()
@Entity(name = "subtasks")
public class SubTask extends TaskSuperClass {

    @Range(min = 0, max = 100)
    private int progress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore()
    private Task task;

    @Override()
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.append(super.getTitle()).append(getId());
        return hashCodeBuilder.toHashCode();
    }

}
