package com.surveyapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    private String id; // MongoDB ObjectId for consistency with other models
    private String text; // The question text
    private List<String> options; // Options for multiple choice (if any)
    private boolean multipleChoice; // Whether the question supports multiple choices
    private String answerType; // (optional) Type of response (e.g., "text", "multiple", "single")
}
