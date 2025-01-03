package com.surveyapp.service;

import com.surveyapp.model.Response;
import com.surveyapp.model.Survey;
import com.surveyapp.repository.ResponseRepository; // Import ResponseRepository
import com.surveyapp.repository.SurveyRepository;
import com.surveyapp.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final ResponseRepository responseRepository;  // Inject ResponseRepository

    // Get all surveys
    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    // Get survey by ID
    public Survey getSurveyById(String surveyId) {
        return surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("Survey not found"));
    }

    // Create a new survey with questions
    @Transactional
    public Survey createSurvey(Survey survey) {
        // Assign IDs to each question
        if (survey.getQuestions() != null) {
            for (Question question : survey.getQuestions()) {
                question.setId(java.util.UUID.randomUUID().toString()); // Generate unique String ID for each question
            }
        }

        survey.setCreatedAt(new Date());

        // Save the survey
        return surveyRepository.save(survey);
    }

    // Update an existing survey
    public Survey updateSurvey(String surveyId, Survey survey) {
        // Retrieve the existing survey from the repository
        Survey existingSurvey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("Survey not found"));

        // Update fields of the existing survey if they are present in the request body
        if (survey.getName() != null) {
            existingSurvey.setName(survey.getName());
        }

        if (survey.getQuestions() != null) {
            // If the questions list is provided, we will update the existing survey's questions
            existingSurvey.setQuestions(survey.getQuestions());
        }

        if (survey.getVersion() != 0) {
            existingSurvey.setVersion(survey.getVersion());  // Optionally update the version
        }

        // Optionally, update the creation date if needed (typically not updated, but could be required in certain cases)
        // If `createdAt` is passed in the request body (though this field usually stays unchanged):
        if (survey.getCreatedAt() != null) {
            existingSurvey.setCreatedAt(survey.getCreatedAt());
        }

        // Save the updated survey back to the repository
        return surveyRepository.save(existingSurvey);
    }


    // Delete a survey
    public void deleteSurvey(String surveyId) {
        if (!surveyRepository.existsById(surveyId)) {
            throw new RuntimeException("Survey not found");
        }
        surveyRepository.deleteById(surveyId);
    }
}
