package com.surveyapp.service;

import com.surveyapp.model.Question;
import com.surveyapp.model.Survey;
import com.surveyapp.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final SurveyRepository surveyRepository; // Injected repository
    private final SurveyService surveyService;       // Injected SurveyService for reuse

    // Get all questions for a specific survey
    public List<Question> getQuestionsForSurvey(String surveyId) {
        ObjectId objectId = new ObjectId(surveyId);
        return surveyRepository.findById(objectId)
                .map(Survey::getQuestions)
                .orElseThrow(() -> new RuntimeException("Survey not found"));
    }

    // Add a new question to a survey
    public Question addQuestionToSurvey(String surveyId, Question question) {
        ObjectId objectId = new ObjectId(surveyId);
        return surveyRepository.findById(objectId)
                .map(survey -> {
                    question.setId(new ObjectId().toHexString()); // Generate unique ID for the question
                    survey.getQuestions().add(question);
                    surveyRepository.save(survey);
                    return question;
                })
                .orElseThrow(() -> new RuntimeException("Survey not found"));
    }

    // Update a question in a survey
    public Question updateQuestionInSurvey(String surveyId, String questionId, Question updatedQuestion) {
        ObjectId objectId = new ObjectId(surveyId);
        return surveyRepository.findById(objectId)
                .map(survey -> {
                    Question question = survey.getQuestions().stream()
                            .filter(q -> q.getId().equals(questionId))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Question not found"));
                    question.setText(updatedQuestion.getText());
                    question.setOptions(updatedQuestion.getOptions());
                    question.setMultipleChoice(updatedQuestion.isMultipleChoice());
                    surveyRepository.save(survey);
                    return question;
                })
                .orElseThrow(() -> new RuntimeException("Survey not found"));
    }

    // Delete a question from a survey
    public void deleteQuestionFromSurvey(String surveyId, String questionId) {
        ObjectId objectId = new ObjectId(surveyId);
        surveyRepository.findById(objectId).ifPresent(survey -> {
            survey.getQuestions().removeIf(q -> q.getId().equals(questionId));
            surveyRepository.save(survey);
        });
    }

    // Get a specific question by ID
    public Question getQuestionById(String surveyId, String questionId) {
        Survey survey = surveyService.getSurveyById(surveyId); // Using SurveyService for fetching the survey
        return survey.getQuestions().stream()
                .filter(question -> question.getId().equals(questionId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Question not found"));
    }
}
