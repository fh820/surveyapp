package com.surveyapp.service;

import com.surveyapp.model.Response;
import com.surveyapp.model.Survey;
import com.surveyapp.repository.ResponseRepository;
import com.surveyapp.repository.SurveyRepository;
import com.surveyapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResponseService {

    private final ResponseRepository responseRepository;
    private final UserRepository userRepository;
    private final SurveyRepository surveyRepository;
    private final MongoTemplate mongoTemplate;

    @Transactional
    public Response submitResponse(Response response, String userId) {
        // Assign userId (can be null for anonymous responses)
        response.setUserId(userId);

        // Ensure the response includes a valid surveyId
        if (response.getSurveyId() == null || response.getSurveyId().isEmpty()) {
            throw new IllegalArgumentException("Survey ID must be provided.");
        }

        // Validate question responses are provided
        if (response.getQuestionResponses() == null || response.getQuestionResponses().isEmpty()) {
            throw new IllegalArgumentException("Response must include answers for questions.");
        }

        // Set the current time (submission time)
        response.setSubmissionTime(new Date());

        // Save the response to the database
        return responseRepository.save(response);
    }

    public Response getResponseById(String responseId) {
        return responseRepository.findById(responseId)
                .orElseThrow(() -> new RuntimeException("Response not found"));
    }

    public List<Response> getResponsesForSurvey(String surveyId) {
        return mongoTemplate.find(Query.query(Criteria.where("surveyId").is(surveyId)), Response.class);
    }

    public Response getUserResponse(String userId, String surveyId) {
        return responseRepository.findByUserIdAndSurveyId(userId, surveyId)
                .orElseThrow(() -> new RuntimeException("Response not found"));
    }

    public Survey createNewVersion(String surveyId) {
        Survey existingSurvey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("Survey not found"));

        Survey newVersion = new Survey();
        newVersion.setName(existingSurvey.getName());
        newVersion.setVersion(existingSurvey.getVersion() + 1);
        newVersion.setQuestions(existingSurvey.getQuestions());
        newVersion.setCreatedAt(new Date());

        return surveyRepository.save(newVersion);
    }

    public Survey getLatestSurvey(String name) {
        Optional<Survey> latestSurvey = surveyRepository.findTopByNameOrderByVersionDesc(name);
        return latestSurvey.orElseThrow(() -> new RuntimeException("Survey not found"));
    }

    public Response startOrResumeSurvey(String userId, String surveyId) {
        Response existingResponse = responseRepository.findByUserIdAndSurveyId(userId, surveyId)
                .orElse(null);

        if (existingResponse != null && !existingResponse.isCompleted()) {
            return existingResponse;
        } else {
            Survey latestSurvey = getLatestSurvey(surveyId);

            Response newResponse = new Response();
            newResponse.setUserId(userId);
            newResponse.setSurveyId(surveyId);
            newResponse.setSurveyVersion(latestSurvey.getVersion());
            newResponse.setProgress(0);
            newResponse.setCompleted(false);

            return responseRepository.save(newResponse);
        }
    }
}
