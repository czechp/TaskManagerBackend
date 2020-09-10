package com.pczech.taskmanager.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data()
@NoArgsConstructor()
@Entity(name = "current_tasks")
public class CurrentTask extends TaskSuperClass {
}
