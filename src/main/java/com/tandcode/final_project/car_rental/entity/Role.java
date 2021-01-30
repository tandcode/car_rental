package com.tandcode.final_project.car_rental.entity;

import org.springframework.security.core.GrantedAuthority;

public enum  Role implements GrantedAuthority{
    USER, ADMIN, MANAGER;

    @Override
    public String getAuthority() {
        return name();
    }
}
