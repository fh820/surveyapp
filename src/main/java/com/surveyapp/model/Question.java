package com.surveyapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private String id;
    private String text;
    private List<String> options;
    private boolean multipleChoice;
}
