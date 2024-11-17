package com.surveyapp.repository;

import com.surveyapp.model.Survey;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends MongoRepository<Survey, ObjectId> {
}
