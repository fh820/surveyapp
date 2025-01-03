package com.surveyapp.controller;

import com.surveyapp.model.Response;
import com.surveyapp.model.User;
import com.surveyapp.repository.UserRepository;
import com.surveyapp.service.ResponseService;
import com.surveyapp.utility.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/responses")
@RequiredArgsConstructor
public class ResponseController {

    private final ResponseService responseService;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    // Submit a response to a survey
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/submit")
    public ResponseEntity<?> submitResponse(@RequestBody Response response, HttpServletRequest request) {
        // Extract JWT from Authorization header
        String authorizationHeader = request.getHeader("Authorization");
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);  // Extract token
        }

        // Extract username from the JWT
        String username = jwtUtils.extractUsername(jwt);

        // Retrieve user from the database
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Set the userId and submission time
        response.setUserId(user.getId());
        response.setSubmissionTime(new Date());

        // Save the response
        responseService.submitResponse(response, user.getId().toString());

        return ResponseEntity.ok("Response submitted successfully");
    }

    // Get a specific response by ID
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{responseId}")
    public Response getResponseById(@PathVariable String responseId) {
        return responseService.getResponseById(responseId);
    }

    // Get all responses for a survey
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/survey/{surveyId}")
    public List<Response> getResponsesForSurvey(@PathVariable String surveyId) {
        return responseService.getResponsesForSurvey(surveyId);
    }

    // Start or resume a survey for a user (similar to the start/resume survey functionality)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/start-or-resume/{userId}/{surveyId}")
    public ResponseEntity<Response> startOrResumeSurvey(@PathVariable String userId, @PathVariable String surveyId) {
        Response response = responseService.startOrResumeSurvey(userId, surveyId); // Corrected to use responseService
        return ResponseEntity.ok(response);
    }
}
