package com.surveyapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "surveys") // MongoDB collection for surveys
public class Survey {
    @Id
    private ObjectId id; // MongoDB ObjectId
    private String name; // Survey name
    private List<Question> questions; // Embedded questions
}
