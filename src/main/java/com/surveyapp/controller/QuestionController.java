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

    // Get all questions for a specific survey (Accessible by Users and Admins)
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Question> getQuestionsForSurvey(@PathVariable String surveyId) {
        return questionService.getQuestionsForSurvey(surveyId);
    }

    // Get a specific question by ID (Accessible by Users and Admins)
    @GetMapping("/{questionId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Question getQuestionById(@PathVariable String surveyId, @PathVariable String questionId) {
        return questionService.getQuestionById(surveyId, questionId);
    }

    // Add a new question to a survey (Only Admins)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Question addQuestionToSurvey(@PathVariable String surveyId, @RequestBody Question question) {
        return questionService.addQuestionToSurvey(surveyId, question);
    }

    // Update an existing question in a survey (Only Admins)
    @PutMapping("/{questionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Question updateQuestionInSurvey(@PathVariable String surveyId, @PathVariable String questionId, @RequestBody Question question) {
        return questionService.updateQuestionInSurvey(surveyId, questionId, question);
    }

    // Delete a question from a survey (Only Admins)
    @DeleteMapping("/{questionId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestionFromSurvey(@PathVariable String surveyId, @PathVariable String questionId) {
        questionService.deleteQuestionFromSurvey(surveyId, questionId);
    }
}
