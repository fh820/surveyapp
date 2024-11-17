package com.surveyapp.service;

import com.surveyapp.model.Question;
import com.surveyapp.model.Survey;
import com.surveyapp.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;

    // Get all surveys
    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    // Get survey by ID
    public Survey getSurveyById(String surveyId) {
        ObjectId objectId = new ObjectId(surveyId);
        return surveyRepository.findById(objectId)
                .orElseThrow(() -> new RuntimeException("Survey not found"));
    }

    // Create a new survey with questions
    @Transactional
    public Survey createSurvey(Survey survey) {
        // Assign IDs to each question
        if (survey.getQuestions() != null) {
            for (Question question : survey.getQuestions()) {
                question.setId(new ObjectId().toHexString());
            }
        }

        // Save the survey
        return surveyRepository.save(survey);
    }

    // Update an existing survey
    public Survey updateSurvey(String surveyId, Survey survey) {
        ObjectId objectId = new ObjectId(surveyId);
        if (!surveyRepository.existsById(objectId)) {
            throw new RuntimeException("Survey not found");
        }
        survey.setId(objectId);
        return surveyRepository.save(survey);
    }

    // Delete a survey
    public void deleteSurvey(String surveyId) {
        ObjectId objectId = new ObjectId(surveyId);
        if (!surveyRepository.existsById(objectId)) {
            throw new RuntimeException("Survey not found");
        }
        surveyRepository.deleteById(objectId);
    }


}
