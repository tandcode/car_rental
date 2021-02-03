package com.tandcode.final_project.car_rental.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;

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

    @NotBlank(message="Photo name is required")
    String photoName;

    @ManyToOne
    @JoinColumn(name="car_brand_id")
    CarBrand carBrand;

    @NotBlank(message="Car model is required")
    String carModel;

    @ManyToOne
    @JoinColumn(name="quality_class_id")
    QualityClass qualityClass;

    //price by one day
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 6, fraction = 2)
    BigDecimal rentPrice;

    Boolean isInUsage;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    Collection<RentOrder> rentOrders = new HashSet<>();

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", photoName='" + photoName + '\'' +
                ", carBrand=" + carBrand +
                ", carModel='" + carModel + '\'' +
                ", qualityClass=" + qualityClass +
                ", rentPrice=" + rentPrice +
                ", isInUsage=" + isInUsage +
                '}';
    }
}
