package com.example.mbti.DTO;

import com.example.mbti.Entity.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class QuestionResponse {
    private List<Question> questionList;
    private String token;
}
