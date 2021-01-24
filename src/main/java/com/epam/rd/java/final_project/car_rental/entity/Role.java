package com.epam.rd.java.final_project.car_rental.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.ManyToMany;
import java.util.Set;

public enum  Role implements GrantedAuthority{
    USER, ADMIN, MANAGER;

//    @ManyToMany
//    private Set<User> users;

    @Override
    public String getAuthority() {
        return name();
    }
}
