package com.surveyapp.controller;

import com.surveyapp.model.Question;
import com.surveyapp.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/surveys/{surveyId}/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    // Get all questions for a specific survey
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Question> getQuestionsForSurvey(@PathVariable String surveyId) {
        return questionService.getQuestionsForSurvey(surveyId);  // Pass surveyId as String
    }

    // Get a specific question by its ID
    @GetMapping("/{questionId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Question getQuestionById(@PathVariable String surveyId, @PathVariable String questionId) {
        return questionService.getQuestionById(surveyId, questionId);
    }

    // Add a new question to a survey
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Question addQuestionToSurvey(@PathVariable String surveyId, @RequestBody Question question) {
        return questionService.addQuestionToSurvey(surveyId, question);  // Pass surveyId as String
    }

    // Update a specific question in a survey
    @PutMapping("/{questionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Question updateQuestionInSurvey(@PathVariable String surveyId, @PathVariable String questionId, @RequestBody Question question) {
        return questionService.updateQuestionInSurvey(surveyId, questionId, question);  // Pass both as Strings
    }

    // Delete a specific question from a survey
    @DeleteMapping("/{questionId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestionFromSurvey(@PathVariable String surveyId, @PathVariable String questionId) {
        questionService.deleteQuestionFromSurvey(surveyId, questionId);  // Pass both as Strings
    }
}
