package com.surveyapp.service;

import com.surveyapp.model.Question;
import com.surveyapp.model.Survey;
import com.surveyapp.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final SurveyRepository surveyRepository; // Injected repository
    private final SurveyService surveyService;       // Injected SurveyService for reuse

    // Get all questions for a specific survey
    public List<Question> getQuestionsForSurvey(String surveyId) {
        return surveyRepository.findById(surveyId)
                .map(Survey::getQuestions)
                .orElseThrow(() -> new RuntimeException("Survey not found with ID: " + surveyId));
    }

    // Add a new question to a survey
    public Question addQuestionToSurvey(String surveyId, Question question) {
        return surveyRepository.findById(surveyId)
                .map(survey -> {
                    // Generate a unique string ID for the question
                    question.setId(java.util.UUID.randomUUID().toString());
                    survey.getQuestions().add(question);
                    surveyRepository.save(survey);
                    return question;
                })
                .orElseThrow(() -> new RuntimeException("Survey not found with ID: " + surveyId));
    }

    // Update a question in a survey
    public Question updateQuestionInSurvey(String surveyId, String questionId, Question updatedQuestion) {
        return surveyRepository.findById(surveyId)
                .map(survey -> {
                    Question question = survey.getQuestions().stream()
                            .filter(q -> q.getId().equals(questionId))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Question not found with ID: " + questionId + " in survey with ID: " + surveyId));
                    question.setText(updatedQuestion.getText());
                    question.setOptions(updatedQuestion.getOptions());
                    question.setMultipleChoice(updatedQuestion.isMultipleChoice());
                    surveyRepository.save(survey);
                    return question;
                })
                .orElseThrow(() -> new RuntimeException("Survey not found with ID: " + surveyId));
    }

    // Delete a question from a survey
    public void deleteQuestionFromSurvey(String surveyId, String questionId) {
        surveyRepository.findById(surveyId).ifPresentOrElse(survey -> {
            boolean removed = survey.getQuestions().removeIf(q -> q.getId().equals(questionId));
            if (!removed) {
                throw new RuntimeException("Question not found with ID: " + questionId + " in survey with ID: " + surveyId);
            }
            surveyRepository.save(survey);
        }, () -> {
            throw new RuntimeException("Survey not found with ID: " + surveyId);
        });
    }

    // Get a specific question by ID
    public Question getQuestionById(String surveyId, String questionId) {
        // Fetch the survey
        Survey survey = surveyService.getSurveyById(surveyId);

        // Find the question
        return survey.getQuestions().stream()
                .filter(question -> question.getId().equals(questionId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + questionId + " in survey with ID: " + surveyId));
    }
}
