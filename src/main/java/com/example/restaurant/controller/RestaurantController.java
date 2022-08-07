package com.example.restaurant.controller;


import com.example.restaurant.enums.Status;
import com.example.restaurant.exceptions.*;
import com.example.restaurant.model.*;
import com.example.restaurant.repositories.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;
import lombok.*;

import java.io.InvalidObjectException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.*;



@RestController
@RequestMapping
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantReviewRepository restaurantReviewRepository;
    private final UserRepository userRepository;

    public RestaurantController(RestaurantRepository restaurantRepository, RestaurantReviewRepository restaurantReviewRepository, UserRepository userRepository) {

        this.restaurantRepository = restaurantRepository;
        this.restaurantReviewRepository = restaurantReviewRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/restaurants/all")
    public Iterable<Restaurant> getAllRestaurants() {
        return this.restaurantRepository.findAll();
    }

    @PostMapping("/restaurant/new")
    public Restaurant newRestaurant(@RequestBody @NotNull Restaurant newRestaurant) throws InvalidRestaurantException {
        if (newRestaurant.getName() == null) {
            throw new InvalidRestaurantException("Name required!!!");
        }
        if( this.restaurantRepository.findRestaurantByNameAndZipcode(newRestaurant.getName(), newRestaurant.getZipcode()).isPresent()){
            throw new InvalidRestaurantException("Restaurant with same name and zipcode already exists!!!");
        }
        this.restaurantRepository.save(newRestaurant);
        return newRestaurant;
    }

    @GetMapping("/restaurant/{id}")
    public Restaurant getRestaurantById(@PathVariable(name = "id") Long id) throws InvalidRestaurantException {
        Optional<Restaurant> getRestaurant = this.restaurantRepository.findRestaurantById(id);
        if(getRestaurant.isEmpty()) {
            throw new InvalidRestaurantException("Restaurant doesn't exist");
        }
        return getRestaurant.get();
    }

    @GetMapping("/restaurants/{zipcode}")
    public Iterable<Restaurant> getByZipcode(@PathVariable(name = "zipcode") Integer zipcode,
                                             @RequestParam(name = "eggAllergy", required = false) Double eggAllergy,
                                             @RequestParam(name = "dairyAllergy", required = false) Double dairyAllergy,
                                             @RequestParam(name = "peanutAllergy", required = false) Double peanutAllergy,
                                             @RequestParam(name = "mostImportant", required = false) String mostImportant) {
        ArrayList<Restaurant> restaurantReturn;

        if (eggAllergy != null) {
            if (dairyAllergy != null) {
                if (peanutAllergy != null) {
                    restaurantReturn = (ArrayList<Restaurant>) this.restaurantRepository.findRestaurantsByZipcodeAndEggGreaterThanAndDairyGreaterThanAndPeanutGreaterThan(zipcode, eggAllergy, dairyAllergy, peanutAllergy);
                }
                restaurantReturn = (ArrayList<Restaurant>) this.restaurantRepository.findRestaurantsByZipcodeAndEggGreaterThanAndDairyGreaterThan(zipcode, eggAllergy, dairyAllergy);
            }
            else {
                if (peanutAllergy != null) {
                    restaurantReturn = (ArrayList<Restaurant>) this.restaurantRepository.findRestaurantsByZipcodeAndEggGreaterThanAndPeanutGreaterThan(zipcode, eggAllergy, peanutAllergy);
                }
                restaurantReturn = (ArrayList<Restaurant>) this.restaurantRepository.findRestaurantsByZipcodeAndEggGreaterThan(zipcode, eggAllergy);
            }
        }
        else {
            if (dairyAllergy != null) {
                if (peanutAllergy != null) {
                    restaurantReturn = (ArrayList<Restaurant>) this.restaurantRepository.findRestaurantsByZipcodeAndDairyGreaterThanAndPeanutGreaterThan(zipcode, dairyAllergy, peanutAllergy);
                }
                else {
                    if (peanutAllergy != null) {
                        restaurantReturn = (ArrayList<Restaurant>) this.restaurantRepository.findRestaurantsByZipcodeAndPeanutGreaterThan(zipcode, peanutAllergy);
                    }
                    restaurantReturn = (ArrayList<Restaurant>) this.restaurantRepository.findRestaurantsByZipcodeAndDairyGreaterThan(zipcode, dairyAllergy);
                }
            }
            restaurantReturn = (ArrayList<Restaurant>) this.restaurantRepository.findRestaurantsByZipcode(zipcode);
        }
        if (dairyAllergy != null ^ eggAllergy != null ^ peanutAllergy != null) {
            if (dairyAllergy != null) {
                Collections.sort(restaurantReturn, Collections.reverseOrder(new SortByDairy()));
            } else if (eggAllergy != null) {
                Collections.sort(restaurantReturn, Collections.reverseOrder(new SortByEgg()));
            }
            Collections.sort(restaurantReturn, Collections.reverseOrder(new SortByPeanut()));
        }
        else {
            if (mostImportant.equalsIgnoreCase("dairy")) {
                Collections.sort(restaurantReturn, Collections.reverseOrder(new SortByDairy()));
            } else if (mostImportant.equalsIgnoreCase("egg")) {
                Collections.sort(restaurantReturn, Collections.reverseOrder(new SortByEgg()));
            } else Collections.sort(restaurantReturn, Collections.reverseOrder(new SortByPeanut()));
        }
        return restaurantReturn;
    }
}


class SortByEgg implements Comparator<Restaurant> {

    @Override
    public int compare(@NotNull Restaurant o1, @NotNull Restaurant o2) {
        return (int) (o1.getEgg() - o2.getEgg()) * 100;
    }
}

class SortByDairy implements Comparator<Restaurant> {

    @Override
    public int compare(@NotNull Restaurant o1, @NotNull Restaurant o2) {
        return (int) (o1.getDairy() - o2.getDairy()) * 100;
    }
}

class SortByPeanut implements Comparator<Restaurant> {

    @Override
    public int compare(@NotNull Restaurant o1, @NotNull Restaurant o2) {
        return (int) (o1.getPeanut() - o2.getPeanut()) * 100;
    }
}
/*
class SortByCity implements Comparator<Restaurant> {

    @Override
    public int compare(Restaurant o1, Restaurant o2) {
        return o1.getCity().compareTo(o2.getCity());
    }
}

class SortByState implements Comparator<Restaurant> {

    @Override
    public int compare(Restaurant o1, Restaurant o2) {
        return o1.getState().compareTo(o2.getState());
    }
}

 */