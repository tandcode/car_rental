package com.epam.rd.java.final_project.car_rental.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String photoName;

    String carBrand;

    String carModel;

    @Enumerated(EnumType.STRING)
    QualityClass qualityClass;

    //price by one day
    BigDecimal rentPrice;

    public static enum QualityClass {
        AFFORDABLE, DECENT, ELITE;
    }
}
