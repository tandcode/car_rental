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
public class QualityClass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message="Name is required")
    @Column(name = "quality_class_name")
    String name;

    @OneToMany(mappedBy = "qualityClass")
    Collection<Car> cars;

    @Override
    public String toString() {
        return "QualityClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
