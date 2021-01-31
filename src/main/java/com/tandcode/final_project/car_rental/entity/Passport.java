package com.tandcode.final_project.car_rental.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message="First name is required")
    String firstName;

    @NotBlank(message="Last name is required")
    String lastName;

    @Pattern(regexp = "^\\w{2}\\d{6}$", message = "Must be formatted XX000000")
    @Column(unique = true)
    String passportNumber;

    @ManyToOne
    @JoinColumn(name="user_id")
    User user;

    @OneToMany(mappedBy = "passport")
    Collection<RentOrder> rentOrders;
}
