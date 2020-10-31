package com.pczech.taskmanager.domain;

import lombok.Getter;

@Getter()
public enum TaskPriority {
    LOW("Niski"),
    MEDIUM("Średni"),
    HIGH("Wysoki");

    private String name;

    TaskPriority(String name) {
        this.name = name;
    }
}
