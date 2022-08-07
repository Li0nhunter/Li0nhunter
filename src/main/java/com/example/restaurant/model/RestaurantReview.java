package com.example.restaurant.model;


import com.example.restaurant.enums.*;
import com.example.restaurant.exceptions.InvalidRestaurantException;
import com.example.restaurant.exceptions.InvalidReviewException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;

@Entity()
@Table(name = "RESTAURANT_REVIEW")
public class RestaurantReview {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    @Getter @Setter private Long id;

    @Column(name = "restaurant")
    @Getter @Setter private Long restaurant;

    @Column(name = "submitter")
    @Getter @Setter private String submitter;

    @Column(name = "peanut")
    @Getter @Setter private Integer peanut;

    @Column(name = "dairy")
    @Getter @Setter private Integer dairy;

    @Column(name = "egg")
    @Getter @Setter private Integer egg;

    @Column(name = "commentary")
    @Getter @Setter private String commentary;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Getter @Setter private Status status;

    public RestaurantReview(){}

    public RestaurantReview(Long restaurant, String submitter) {
        this.restaurant = restaurant;
        this.submitter = submitter;
    }

    public RestaurantReview (
            Long restaurant,
            String submitter,
            Integer peanut,
            Integer dairy,
            Integer egg,
            String commentary
    ) throws InvalidReviewException {

        this.restaurant = restaurant;
        this.submitter = submitter;
        if(!checkBetween(peanut)) throw new InvalidReviewException("Invalid peanut");
        this.peanut = peanut;
        if(!checkBetween(dairy)) throw new InvalidReviewException("Invalid dairy");
        this.dairy = dairy;
        if(!checkBetween(egg)) throw new InvalidReviewException("Invalid egg");
        this.egg = egg;
        this.commentary = commentary;
    }

    private Boolean checkBetween(Integer p) {
        return (p < 6 && p > 0);
    }
}


