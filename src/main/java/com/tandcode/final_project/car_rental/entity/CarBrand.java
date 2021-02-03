package com.tandcode.final_project.car_rental.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class CarBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message="Name is required")
    @Column(name = "car_brand_name")
    String name;

    @OneToMany(mappedBy = "carBrand")
    Collection<Car> cars;

    @Override
    public String toString() {
        return "CarBrand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
