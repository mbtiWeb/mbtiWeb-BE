package com.example.mbti.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerItem {
    private Long questionId; // 문항 번호
    private Integer selectedScore; // 문항 점수 : 1~7점
}
