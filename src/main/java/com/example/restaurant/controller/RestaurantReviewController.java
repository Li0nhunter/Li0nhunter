package com.example.restaurant.controller;

import com.example.restaurant.enums.Status;
import com.example.restaurant.exceptions.InvalidReviewException;
import com.example.restaurant.model.RestaurantReview;
import com.example.restaurant.repositories.RestaurantRepository;
import com.example.restaurant.repositories.RestaurantReviewRepository;
import com.example.restaurant.repositories.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
public class RestaurantReviewController {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantReviewRepository restaurantReviewRepository;
    private final UserRepository userRepository;

    public RestaurantReviewController(RestaurantRepository restaurantRepository, RestaurantReviewRepository restaurantReviewRepository, UserRepository userRepository) {

        this.restaurantRepository = restaurantRepository;
        this.restaurantReviewRepository = restaurantReviewRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public Iterable<RestaurantReview> getAllRestaurantReviews() {
        return this.restaurantReviewRepository.findAll();
    }

    @PostMapping("/new")
    public RestaurantReview newReview(@RequestBody @NotNull RestaurantReview newReview) throws InvalidReviewException {

        if (newReview.getSubmitter() == null) {
            throw new InvalidReviewException("Submitter can't be null!!!");
        }
        if (this.userRepository.findByDisplayName(newReview.getSubmitter()).isEmpty()) {
            throw new InvalidReviewException("Submitter doesn't exist!!!");
        }
        if (this.restaurantRepository.findRestaurantById(newReview.getRestaurant()).isEmpty()) {
            throw new InvalidReviewException("Restaurant doesn't exist!!!");
        }
        newReview.setStatus(Status.PENDING);
        this.restaurantReviewRepository.save(newReview);
        return newReview;

    }
}
