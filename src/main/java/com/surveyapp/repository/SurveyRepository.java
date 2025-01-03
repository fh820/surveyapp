package com.surveyapp.repository;

import com.surveyapp.model.Survey;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SurveyRepository extends MongoRepository<Survey, String> {

    // Find the most recent version of a survey by name
    Optional<Survey> findTopByNameOrderByVersionDesc(String name);
}
