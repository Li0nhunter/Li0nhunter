package com.example.restaurant.repositories;

import com.example.restaurant.enums.Status;
import com.example.restaurant.model.RestaurantReview;
import org.springframework.data.repository.CrudRepository;


import java.util.List;

public interface RestaurantReviewRepository extends CrudRepository<RestaurantReview, Integer> {
    List<RestaurantReview> findByStatus(Status status);
    List<RestaurantReview> findByStatusAndRestaurant(Status status, Long restaurant);
}
