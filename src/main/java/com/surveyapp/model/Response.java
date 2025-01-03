package com.surveyapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "responses") // MongoDB collection for responses
public class Response {

    @Id
    private String id;

    private String surveyId; // Reference to the survey ID (MongoDB ObjectId)
    private String userId; // Reference to the user ID (MongoDB ObjectId)
    private List<QuestionResponse> questionResponses; // List of responses to each question
    private double surveyVersion; // Version of the survey taken
    private int progress; // Tracks progress of survey completion
    @CreatedDate // Automatically set the submission time when the response is created
    private Date submissionTime;
    private boolean completed; // Whether the survey was completed or not

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionResponse {
        private String questionId; // Reference to the question ID (MongoDB ObjectId)
        private String response; // The user’s response for the question (can be free-form or option-based)
    }
}
