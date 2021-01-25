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

    @ManyToOne
    @JoinTable(name = "car_brand",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "brand_id"))
    CarBrand carBrand;

    @ManyToOne
    @JoinTable(name = "car_model",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "model_id"))
    CarModel carModel;

    @Enumerated(EnumType.STRING)
    QualityClass qualityClass;

    //price by one day
    BigDecimal rentPrice;

    public static enum QualityClass {
        AFFORDABLE, DECENT, ELITE;
    }
}
