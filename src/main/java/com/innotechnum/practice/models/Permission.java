package com.innotechnum.practice.models;

public enum Permission {
    STAFF_READ("staff:read"),
    STAFF_WRITE("staff:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
