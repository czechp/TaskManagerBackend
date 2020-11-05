package com.pczech.taskmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull()
    @NotEmpty()
    @Length(min = 5, max = 255)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore()
    private Task task;

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(content);
        return hcb.toHashCode();
    }
}
