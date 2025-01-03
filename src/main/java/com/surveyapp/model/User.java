package com.surveyapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id; // MongoDB ObjectId for user

    private String username; // Username for the user
    private String password; // User's password
    private Role role; // Role of the user (e.g., Admin, Customer)
}
