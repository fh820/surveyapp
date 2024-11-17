package com.surveyapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "responses") // MongoDB collection for responses
public class Response {

    @Id
    private ObjectId id; // MongoDB’s built-in ObjectId

    private ObjectId surveyId; // Reference to the survey ID (MongoDB ObjectId)
    private ObjectId userId; // Reference to the user ID (MongoDB ObjectId)
    private List<QuestionResponse> questionResponses; // List of responses to each question

    @CreatedDate // Automatically set the submission time when the response is created
    private Date submissionTime;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionResponse {
        private ObjectId questionId; // Reference to the question ID (MongoDB ObjectId)
        private String response; // The user’s response for the question
    }
}
