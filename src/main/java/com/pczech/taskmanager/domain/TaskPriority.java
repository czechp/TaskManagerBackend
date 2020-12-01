package com.pczech.taskmanager.domain;

import lombok.Getter;

@Getter()
public enum TaskPriority {
    LOW("Niski"),
    MEDIUM("Średni"),
    HIGH("Wysoki");

    private final String name;

    TaskPriority(String name) {
        this.name = name;
    }
}
