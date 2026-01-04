package com.example.mbti.DTO;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

// 답변 리스트로 받아서 점수 처리 시 반복문 사용 가능
@Getter
@AllArgsConstructor
public class AnswerRequest {
    private List<AnswerRequestItem> answers;
    private String token;
}
