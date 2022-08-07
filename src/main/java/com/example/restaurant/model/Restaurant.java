package com.example.restaurant.model;

import com.example.restaurant.exceptions.InvalidRestaurantException;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "RESTAURANT")
public class Restaurant {
    @Id
    @GeneratedValue
    @Getter @Setter private Long id;

    @Column(name = "name")
    @Getter @Setter private String name;

    @Column(name = "peanut")
    @Getter @Setter private Double peanut;

    @Column(name = "egg")
    @Getter @Setter private Double egg;

    @Column(name = "dairy")
    @Getter @Setter private Double dairy;

    @Column(name = "address")
    @Getter @Setter private String address;

    @Column(name = "city")
    @Getter @Setter private String city;

    @Column(name = "state")
    @Getter @Setter private String state;

    @Column(name = "country")
    @Getter @Setter private String country;

    @Column(name = "zipcode")
    @Getter @Setter private Integer zipcode;

    public Restaurant(){}
    public Restaurant(
            String name,
            Double peanut,
            Double egg,
            Double dairy,
            String address,
            String city,
            String state,
            String country,
            Integer zipcode
    ) {
        this.name = name;
        this.peanut = peanut;
        this.egg = egg;
        this.dairy = dairy;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipcode = zipcode;
    }

    public Restaurant(
            String name,
            String address,
            String city,
            String state,
            String country,
            Integer zipcode
            ) throws InvalidRestaurantException {
        this.name = name;
        this.peanut = 0.0;
        this.egg = 0.0;
        this.dairy = 0.0;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        if(!validateZipcode(zipcode)) throw new InvalidRestaurantException("Invalid zipcode");
        this.zipcode = zipcode;
    }

    public Restaurant(String name) {
        this.name = name;
        this.peanut = 0.0;
        this.egg = 0.0;
        this.dairy = 0.0;
        this.address = null;
        this.city = null;
        this.state = null;
        this.country = null;
        this.zipcode = null;
    }


    private @NotNull Boolean validateZipcode(@NotNull Integer z) {
        Pattern zips = Pattern.compile("[0-9] {5}");
        Matcher zipMatch = zips.matcher(z.toString());
        return zipMatch.matches();
    }



}
