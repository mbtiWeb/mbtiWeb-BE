package com.example.mbti.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.mbti.DTO.AnswerRequest;
import com.example.mbti.DTO.MbtiResultResponse;
import com.example.mbti.DTO.AnswerItem;
import com.example.mbti.Entity.Question;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class ResultCalcService {
    private final QuestionService questionService;

    public MbtiResultResponse calculateMbtiType(AnswerRequest request) {
        // 토큰, 답변 리스트 불러오기
        String token = request.getToken();
        List<AnswerItem> answers = request.getAnswers();

        Map<String, Integer> scores = new HashMap<>();

        for (AnswerItem item : answers) {
            // 개별 응답 항목을 처리하고 점수를 scores 맵에 누적
            processAnswer(item, scores);
        }

        String finalMbti = determineMbtiType(scores);

        // DTO에 결과와 토큰을 담아 반환
        return new MbtiResultResponse(
                finalMbti,
                scores,
                token // 👈 토큰 전달
        );
    }

    private void processAnswer(AnswerItem item, Map<String, Integer> scores) {
        Long questionId = item.getQuestionId();
        Integer selectedScore = item.getSelectedScore();

        // 질문 Entity 조회
        Question questionEntity = questionService.getQuestionById(questionId);

        // axis, isReverse 추출
        String axis = getAxisByQuestionNumber(questionEntity.getNumber());
        Boolean isReversed = questionEntity.getIs_reversed();

        // 최종 점수 계산 및 보정
        int finalScore = selectedScore;
        if (isReversed != null && isReversed) {
            finalScore = 8 - selectedScore;
        }

        // 보정된 점수를 해당 축에 누적
        int currentTotalScore = scores.getOrDefault(axis, 0);
        scores.put(axis, currentTotalScore + finalScore);
    }

    // 질문 번호로 축을 결정
    private String getAxisByQuestionNumber(Integer number) {
        if (number >= 1 && number <= 6) {
            return "EI";
        } else if (number >= 7 && number <= 12) {
            return "SN";
        } else if (number >= 13 && number <= 18) {
            return "TF";
        } else if (number >= 19 && number <= 24) {
            return "JP";
        } else if (number >= 25 && number <= 27) {
            return "Social Introvert";
        } else if (number >= 28 && number <= 30) {
            return "Thinking Introvert";
        } else if (number >= 31 && number <= 33) {
            return "Anxious Introvert";
        } else if (number >= 34 && number <= 36) {
            return "Restrained Introvert";
        } else if (number >= 37 && number <= 39) {
            return "Social Extrovert";
        } else if (number >= 40 && number <= 42) {
            return "Agentic Extrovert";
        } else if (number >= 43 && number <= 45) {
            return "Affiliative Extrovert";
        } else if (number >= 46 && number <= 48) {
            return "Balanced Ambivert";
        } else if (number >= 49 && number <= 51) {
            return "Contextual Ambivert";
        } else if (number >= 52 && number <= 54) {
            return "Mild Ambivert";
        } else if (number >= 55 && number <= 57) {
            return "Reactive Omnivert";
        } else if (number >= 58 && number <= 60) {
            return "Adaptive Omnivert";
        }
        // 정의되지 않은 문항 번호에 대한 예외 처리
        throw new IllegalArgumentException("정의되지 않은 문항 번호입니다: " + number);
    }

    private String determineMbtiType(Map<String, Integer> axisScores) {

        // midpoint : 6문항 * (7점 척도 중 중간값 4점) = 24
        final int MIDPOINT = 24;

        String mbti = String.valueOf(axisScores.getOrDefault("EI", 0) > MIDPOINT ? 'E' : 'I') +
                (axisScores.getOrDefault("SN", 0) > MIDPOINT ? 'S' : 'N') +
                (axisScores.getOrDefault("TF", 0) > MIDPOINT ? 'T' : 'F') +
                (axisScores.getOrDefault("JP", 0) > MIDPOINT ? 'J' : 'P');

        return mbti;
    }

    private List<String> determineSubtype(Map<String, Integer> axisScores) {
        int maxScore = axisScores.entrySet().stream()
                // 키 길이가 2를 초과하는 subtype만 필터링
                .filter(entry -> entry.getKey().length() > 2)

                .mapToInt(Map.Entry::getValue)
                .max()
                .orElse(Integer.MIN_VALUE);

        if (maxScore == Integer.MIN_VALUE) {
            // 서브 타입 점수가 아예 없거나, 모두 Integer.MIN_VALUE일 경우
            return List.of();
        }

        // 최대 점수와 일치하는 모든 서브 타입 키를 수집
        return axisScores.entrySet().stream()

                // 키 길이가 2를 초과하는 subtype만 필터링
                .filter(entry -> entry.getKey().length() > 2)

                .filter(entry -> entry.getValue() == maxScore) // 최대 점수와 일치
                .map(Map.Entry::getKey) // 서브 타입 이름(예: "Adaptive Omnivert")을 반환
                .toList();
    }
}
