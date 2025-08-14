package com.asu_cambodia.asu.enumStirng;

public enum RoleUser {
    SUPER_ADMIN,
    ADMIN,
    USER;
    public String getValueAdminRole() {
        return "ROLE_" + this.name();
    }
}
