package com.surveyapp.controller;

import com.surveyapp.model.User;
import com.surveyapp.service.CustomUserDetailsService;
import com.surveyapp.service.UserService;
import com.surveyapp.utility.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    private final JwtUtils jwtUtils;

    // Endpoint to register a new user
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // Endpoint to log in a user and generate a JWT token
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
            // Authenticate the user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            // Load user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

            // Retrieve authenticated user from the database to get the user ID
            User authenticatedUser = userService.findByUsername(user.getUsername());

            // Generate JWT token
            String token = jwtUtils.generateToken(userDetails.getUsername());

            // Return the token in the response
            return ResponseEntity.ok().body("{\"token\": \"" + token + "\"}");
        } catch (Exception e) {
            // Return unauthorized response if authentication fails
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
