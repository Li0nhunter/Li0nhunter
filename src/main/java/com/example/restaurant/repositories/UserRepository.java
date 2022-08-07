package com.example.restaurant.repositories;

import com.example.restaurant.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByDisplayName(String displayName);

}
