package com.surveyapp.repository;

import com.surveyapp.model.Response;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResponseRepository extends MongoRepository<Response, String> {

    // Find all responses for a given surveyId
    List<Response> findBySurveyId(String surveyId);

    // Find all responses for a given userId
    List<Response> findByUserId(String userId);

    // Find responses by surveyId and completed status (useful for filtering incomplete surveys)
    List<Response> findBySurveyIdAndCompleted(String surveyId, boolean completed);

    // Fetch the latest response by userId and surveyId (in case of updates)
    Optional<Response> findTopByUserIdAndSurveyIdOrderBySubmissionTimeDesc(String userId, String surveyId);

    // Find By UserId and SurveyId
    Optional<Response> findByUserIdAndSurveyId(String userId, String surveyId);
}
