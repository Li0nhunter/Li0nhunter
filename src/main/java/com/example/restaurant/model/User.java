package com.example.restaurant.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "TABLE_USER")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    @Getter @Setter private Long id;

    @Column(name = "display_name")
    @Getter @Setter private String displayName;

    @Column(name = "city")
    @Getter @Setter private String city;

    @Column(name = "zipcode")
    @Getter @Setter private Integer zipcode;

    @Column(name = "egg_allergy")
    @Getter @Setter private Boolean eggAllergy;

    @Column(name = "dairy_allergy")
    @Getter @Setter private Boolean dairyAllergy;

    @Column(name = "peanut_allergy")
    @Getter @Setter private Boolean peanutAllergy;

    public User(){}

    public User(String displayName, String city, Integer zipcode, Boolean eggAllergy, Boolean dairyAllergy, Boolean peanutAllergy) {
        this.displayName = displayName;
        this.city = city;
        this.zipcode = zipcode;
        this.eggAllergy = eggAllergy;
        this.dairyAllergy = dairyAllergy;
        this.peanutAllergy = peanutAllergy;
    }
}
