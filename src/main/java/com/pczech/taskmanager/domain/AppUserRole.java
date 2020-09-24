package com.pczech.taskmanager.domain;

import java.util.Arrays;
import java.util.Optional;

public enum AppUserRole {
    USER,
    SUPERUSER,
    DIRECTOR,
    ADMIN;

    AppUserRole() {
    }

    public static Optional<AppUserRole> getRole(String role) {
        return Arrays.stream(AppUserRole.values())
                .filter(x -> x.toString().equalsIgnoreCase(role.toUpperCase()))
                .findAny();
    }
}
