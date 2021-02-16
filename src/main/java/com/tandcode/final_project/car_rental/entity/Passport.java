package com.tandcode.final_project.car_rental.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode

@Entity
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Exclude
    private Long id;

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @Pattern(regexp = "^\\w{2}\\d{6}$")
    @Column(unique = true)
    String passportNumber;

    @ManyToMany
    @JoinTable(
            name = "passport_user",
            joinColumns = {@JoinColumn(name = "passport_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    @JoinColumn(name="user_id")
    @EqualsAndHashCode.Exclude
    Collection<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "passport")
    @EqualsAndHashCode.Exclude
    Collection<RentOrder> rentOrders;

    @Override
    public String toString() {
        return "Passport{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", passportNumber='" + passportNumber +
                '}';
    }
}
