package com.epam.rd.java.final_project.car_rental.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String photoName;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    CarBrand brand;

    @ManyToOne
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    CarModel model;

    //price by one day
    BigDecimal rentPrice;
    @ManyToOne
    @JoinColumn(name = "quality_class_id", referencedColumnName = "id")
    QualityClass qualityClass;
}
