package com.example.restaurant.controller;

import com.example.restaurant.enums.Status;
import com.example.restaurant.exceptions.*;
import com.example.restaurant.model.*;
import com.example.restaurant.repositories.*;
import org.springframework.web.bind.annotation.*;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantReviewRepository restaurantReviewRepository;



    public AdminController (UserRepository userRepository, RestaurantRepository restaurantRepository, RestaurantReviewRepository restaurantReviewRepository) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.restaurantReviewRepository = restaurantReviewRepository;
    }

    @PutMapping("/review/{reviewId}")
    public RestaurantReview adminReview(@PathVariable(name = "reviewId") Integer restaurantReviewId, @RequestBody AdminReviewAction adminReviewAction) throws InvalidReviewException {
        if(this.restaurantReviewRepository.findById(restaurantReviewId).isEmpty()) {
            throw new InvalidReviewException("Restaurant review doesn't exist!!!");
        }
        RestaurantReview adminReview = this.restaurantReviewRepository.findById(restaurantReviewId).get();
        if(this.restaurantRepository.findRestaurantById(adminReview.getRestaurant()).isEmpty()) {
            throw new InvalidReviewException("Restaurant doesn't exist");
        }
        if(adminReviewAction.getAccept()) adminReview.setStatus(Status.ACCEPTED);
        else adminReview.setStatus(Status.REJECTED);

        this.restaurantReviewRepository.save(adminReview);

        if(adminReview.getStatus() == Status.ACCEPTED) {
            updateAllergiesForRestaurant(this.restaurantRepository.findRestaurantById(adminReview.getRestaurant()).get());
        }
        return adminReview;
    }
    @GetMapping("/review/{status}/{id}")
    public List<RestaurantReview> findReviews(@PathVariable(name = "status") String status, @PathVariable(name = "id", required = false) Long id) throws InvalidReviewException {
        Status status1 = statusGen(status);
        if(id == null) {
            return this.restaurantReviewRepository.findByStatus(status1);
        }
        if(this.restaurantRepository.findRestaurantById(id).isEmpty()) {
            throw new InvalidReviewException("Restaurant with that name doesn't exist!!!");
        }
        return this.restaurantReviewRepository.findByStatusAndRestaurant(status1, this.restaurantRepository.findRestaurantById(id).get().getId());
    }

    private Status statusGen(String status) throws InvalidReviewException {
        var sa = Status.ACCEPTED.toString();
        var sp = Status.PENDING.toString();
        var sr = Status.REJECTED.toString();
        Status status1;
        if(sa.equalsIgnoreCase(status)) {
            status1 = Status.ACCEPTED;
        } else if (sp.equalsIgnoreCase(status)) {
            status1 = Status.PENDING;
        } else if (sr.equalsIgnoreCase(status)) {
            status1 = Status.REJECTED;
        } else throw new InvalidReviewException("Invalid status!!!");
        return status1;
    }

    private void updateAllergiesForRestaurant(@NotNull Restaurant restaurant) {
        DecimalFormat twoPlaces = new DecimalFormat("0.00");
        ArrayList<RestaurantReview> listOfReviews = (ArrayList<RestaurantReview>) this.restaurantReviewRepository.findByStatusAndRestaurant(Status.ACCEPTED, restaurant.getId());
        Double eggsTotal = 0.0;
        Double dairyTotal = 0.0;
        Integer reviewTotal = 0;
        Double peanutTotal = 0.0;
        for(RestaurantReview review : listOfReviews) {
            eggsTotal += review.getEgg();
            dairyTotal += review.getDairy();
            peanutTotal += review.getPeanut();
            reviewTotal++;
        }
        restaurant.setEgg(Double.parseDouble(twoPlaces.format(eggsTotal / reviewTotal)));
        restaurant.setDairy(Double.parseDouble(twoPlaces.format(dairyTotal / reviewTotal)));
        restaurant.setPeanut(Double.parseDouble(twoPlaces.format(peanutTotal / reviewTotal)));
        this.restaurantRepository.save(restaurant);

    }
}
