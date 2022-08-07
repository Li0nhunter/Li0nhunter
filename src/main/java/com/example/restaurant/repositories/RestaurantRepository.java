package com.example.restaurant.repositories;

import com.example.restaurant.model.Restaurant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {
    Optional<Restaurant> findRestaurantByName(String name);
    Optional<Restaurant> findRestaurantById(Long id);
    Optional<Restaurant> findRestaurantByNameAndZipcode(String name, Integer zipcode);
    List<Restaurant> findRestaurantsByZipcode(Integer zipcode);
    List<Restaurant> findRestaurantsByZipcodeAndEggGreaterThan(Integer zipcode, Double egg);
    List<Restaurant> findRestaurantsByZipcodeAndDairyGreaterThan(Integer zipcode, Double dairy);
    List<Restaurant> findRestaurantsByZipcodeAndPeanutGreaterThan(Integer zipcode, Double peanut);
    List<Restaurant> findRestaurantsByZipcodeAndEggGreaterThanAndDairyGreaterThan(Integer zipcode, Double egg, Double dairy);
    List<Restaurant> findRestaurantsByZipcodeAndEggGreaterThanAndPeanutGreaterThan(Integer zipcode, Double egg, Double peanut);
    List<Restaurant> findRestaurantsByZipcodeAndDairyGreaterThanAndPeanutGreaterThan(Integer zipcode, Double dairy, Double peanut);
    List<Restaurant> findRestaurantsByZipcodeAndEggGreaterThanAndDairyGreaterThanAndPeanutGreaterThan(Integer zipcode, Double egg, Double dairy, Double peanut);
    List<Restaurant> findRestaurantsByDairyGreaterThanEqual(Double dairy);
    List<Restaurant> findRestaurantsByEggGreaterThanEqual(Double egg);
    List<Restaurant> findRestaurantsByPeanutGreaterThanEqual(Double peanut);

}
