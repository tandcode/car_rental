package com.epam.rd.java.final_project.car_rental.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message="Photo name is required")
    String photoName;

    @NotBlank(message="Car brand is required")
    String carBrand;

    @NotBlank(message="Car model is required")
    String carModel;

    @Enumerated(EnumType.STRING)
    QualityClass qualityClass;

    //price by one day
    @NotBlank(message="Price is required")
    BigDecimal rentPrice;

    public static enum QualityClass {
        AFFORDABLE, DECENT, ELITE;
    }
}
