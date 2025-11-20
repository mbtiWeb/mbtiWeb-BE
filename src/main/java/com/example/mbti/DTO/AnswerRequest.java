package com.example.mbti.DTO;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

// 답변 리스트로 받아서 점수 처리 시 반복문 사용 가능
@Getter
@Setter
public class AnswerRequest {
    private List<AnswerItem> answers;
    private String token;
}
