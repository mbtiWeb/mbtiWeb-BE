package com.example.mbti.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 개별 답변을 담는 DTO
@Getter
@AllArgsConstructor
public class AnswerRequestItem {
    private Integer number; // 문항 번호
    private Integer selectedScore; // 문항 점수 : 1~7점
}
