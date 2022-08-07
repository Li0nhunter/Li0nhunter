package com.example.restaurant.controller;

import com.example.restaurant.exceptions.InvalidUserException;
import com.example.restaurant.model.User;
import com.example.restaurant.repositories.RestaurantRepository;
import com.example.restaurant.repositories.RestaurantReviewRepository;
import com.example.restaurant.repositories.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantReviewRepository restaurantReviewRepository;
    private final UserRepository userRepository;

    public UserController(RestaurantRepository restaurantRepository, RestaurantReviewRepository restaurantReviewRepository, UserRepository userRepository) {

        this.restaurantRepository = restaurantRepository;
        this.restaurantReviewRepository = restaurantReviewRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public Iterable<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @PostMapping("/new")
    public User newUser(@RequestBody @NotNull User newUser) throws InvalidUserException {
        if (newUser.getDisplayName() == null) {
            throw new InvalidUserException("Display name can't be empty!!!");
        }
        if (this.userRepository.findByDisplayName(newUser.getDisplayName()).isPresent()) {
            throw new InvalidUserException("Display name already exists!!!");
        }
        this.userRepository.save(newUser);
        return newUser;
    }

    @GetMapping("/{displayName}")
    public User getUser(@PathVariable String displayName) {
        return this.userRepository.findByDisplayName(displayName).get();
    }

    @PutMapping("/{displayName}")
    public User editUser(@PathVariable String displayName, @RequestBody User updatedUser) throws InvalidUserException {
        if (this.userRepository.findByDisplayName(displayName).isEmpty()) {
            throw new InvalidUserException("No matching display name!!!");
        }
        User oldUser = this.userRepository.findByDisplayName(displayName).get();
        if (!oldUser.getDisplayName().equals(updatedUser.getDisplayName())) {
            throw new InvalidUserException("Display name can't be changed!!!");
        }
        this.userRepository.save(updatedUser);
        return updatedUser;

    }
}
