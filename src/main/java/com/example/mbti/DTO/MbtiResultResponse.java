package com.example.mbti.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class MbtiResultResponse {
    private final String mbtiType; // mbti + subtype

    // 상세 정보 필드 (선택적으로 포함)
    private final Map<String, Integer> scores; // EI, NS, TF, PJ, subtype 점수
    private final LocalDateTime timestamp = LocalDateTime.now(); // 타임스탬프 -> 로그 관리에 용이
    private final String token; // 토큰도 함께 전송
}
