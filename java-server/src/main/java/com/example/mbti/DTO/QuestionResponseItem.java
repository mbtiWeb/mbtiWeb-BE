package com.example.mbti.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionResponseItem {
    private Integer number;
    private Boolean isReversed;
    private String question;
}
