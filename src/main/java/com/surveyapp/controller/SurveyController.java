package com.surveyapp.controller;

import com.surveyapp.model.Survey;
import com.surveyapp.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/surveys")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    // Get all surveys (Accessible by Users and Admins)
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Survey> getAllSurveys() {
        return surveyService.getAllSurveys();
    }

    // Get a survey by ID (Accessible by Users and Admins)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Survey getSurveyById(@PathVariable String id) {
        return surveyService.getSurveyById(id);
    }

    // Create a new survey (Only Admins)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Survey createSurvey(@RequestBody Survey survey) {
        return surveyService.createSurvey(survey);
    }

    // Update an existing survey (Only Admins)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Survey updateSurvey(@PathVariable String id, @RequestBody Survey survey) {
        return surveyService.updateSurvey(id, survey);
    }

    // Delete a survey (Only Admins)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteSurvey(@PathVariable String id) {
        surveyService.deleteSurvey(id);
    }

    // Update specific fields in a survey (Only Admins)
    @PatchMapping("/{surveyId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Survey patchSurvey(@PathVariable String surveyId, @RequestBody Survey survey) {
        Survey existingSurvey = surveyService.getSurveyById(surveyId);

        // Update only the fields that are present in the request body
        if (survey.getName() != null) {
            existingSurvey.setName(survey.getName());
        }
        if (survey.getQuestions() != null) {
            existingSurvey.setQuestions(survey.getQuestions());
        }

        return surveyService.updateSurvey(surveyId, existingSurvey);
    }
}
