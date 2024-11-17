package com.surveyapp.service;

import com.surveyapp.model.Response;
import com.surveyapp.model.User; // Import User model
import com.surveyapp.repository.ResponseRepository;
import com.surveyapp.repository.UserRepository; // Import UserRepository
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime; // Import LocalDateTime
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponseService {

    private final ResponseRepository responseRepository;
    private final UserRepository userRepository; // Inject UserRepository
    private final MongoTemplate mongoTemplate;

    @Transactional
    public Response submitResponse(Response response, String userId) {
        // Convert userId from String to ObjectId if necessary
        ObjectId objectId = new ObjectId(userId);

        // Assign userId (can be null for anonymous responses)
        response.setUserId(objectId);  // Set userId as ObjectId

        // Ensure the response includes a valid surveyId
        if (response.getSurveyId() == null) {
            throw new IllegalArgumentException("Survey ID must be provided.");
        }

        // Validate question responses are provided
        if (response.getQuestionResponses() == null || response.getQuestionResponses().isEmpty()) {
            throw new IllegalArgumentException("Response must include answers for questions.");
        }

        // Set the current time (submission time)
        response.setSubmissionTime(new Date());  // Set submission time using LocalDateTime

        // MongoDB will generate a unique ID if not manually set
        response.setId(new ObjectId());

        // Save the response to the database
        return responseRepository.save(response);
    }

    // Get a response by ID
    public Response getResponseById(String responseId) {
        if (!ObjectId.isValid(responseId)) {
            throw new IllegalArgumentException("Invalid ObjectId format.");
        }
        ObjectId objectId = new ObjectId(responseId);
        return responseRepository.findById(objectId)
                .orElseThrow(() -> new RuntimeException("Response not found"));
    }

    // Get all responses for a specific survey
    public List<Response> getResponsesForSurvey(String surveyId) {
        if (!ObjectId.isValid(surveyId)) {
            throw new IllegalArgumentException("Invalid ObjectId format.");
        }
        ObjectId objectId = new ObjectId(surveyId);
        return mongoTemplate.find(Query.query(Criteria.where("surveyId").is(objectId)), Response.class);
    }

    // To add Survey Versions later
}
