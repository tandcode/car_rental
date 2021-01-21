package com.epam.rd.java.final_project.car_rental.domain;

import org.springframework.security.core.GrantedAuthority;

public enum  Role implements GrantedAuthority{
    USER, ADMIN, MANAGER;

    @Override
    public String getAuthority() {
        return name();
    }
}
