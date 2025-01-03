package com.surveyapp.repository;

import com.surveyapp.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    // Find user by username
    Optional<User> findByUsername(String username);

    // Check if the username already exists in the database (for registration)
    boolean existsByUsername(String username);
}
