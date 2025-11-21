package com.example.mbti.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.Map;

import com.example.mbti.Entity.Mbti;

@Getter
@RequiredArgsConstructor
public class MbtiResultResponse {
    private final Mbti mbti; // mbti
    private final Mbti subtype; // subtype
    private final Map<String, Integer> scores; // EI, NS, TF, PJ, subtype 점수
    private final LocalDateTime timestamp; // 타임스탬프 -> 로그 관리에 용이
    private final String token; // 토큰도 함께 전송
}
