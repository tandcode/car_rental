package com.epam.rd.java.final_project.car_rental.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public enum QualityClass {
    AFFORDABLE, DECENT, ELITE;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
