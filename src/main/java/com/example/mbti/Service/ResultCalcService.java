package com.example.mbti.Service;

import com.example.mbti.DTO.AnswerRequest;
import com.example.mbti.DTO.AnswerRequestItem;
import com.example.mbti.DTO.MbtiResultResponse;
import com.example.mbti.Entity.Question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResultCalcService {
    private final QuestionService questionService;

    public MbtiResultResponse calculateMbtiType(AnswerRequest request) {
        // token, answers, questions 불러오기
        String token = request.getToken();
        List<AnswerRequestItem> answers = request.getAnswers();
        Map<Integer, Question> questionMap = questionService.getAllQuestionsAsMap();
        // 각각의 type별 점수를 계산한다.
        Map<String, Double> scores = new HashMap<>(Map.of(
                "E", -0.5,
                "S", 0.5,
                "T", -0.5,
                "J", -0.5
        ));

        for (AnswerRequestItem item : answers) {
            // 질문 번호를 이용해 답변에 해당하는 질문 검색
            Question question = questionMap.get(item.getNumber());
            // 개별 응답 항목을 처리하고 점수를 scores 맵에 누적
            processAnswer(item, scores, question);
        }
        String mbti = determineMbtiType(scores);
        List<String> subtype = determineSubtype(scores);

        // 결과를 MbtiResultResponse DTO에 담아 반환
        return new MbtiResultResponse(
                mbti,
                subtype,
                scores,
                token
        );
    }

    public void processAnswer(AnswerRequestItem item, Map<String, Double> scores, Question question) {
        Integer selectedScore = item.getSelectedScore(); // 답변에서 고른 점수

        if (question != null) {
            // axis, isReverse 추출
            String type = question.getType();
            Boolean isReversed = question.getIsReversed();

            // 최종 점수 계산 및 보정
            int finalScore = selectedScore;
            if (isReversed != null && isReversed) {
                finalScore = 8 - selectedScore;
            }

            // 보정된 점수를 해당 축에 누적
            double currentTotalScore = scores.getOrDefault(type, 0.0);
            scores.put(type, currentTotalScore + finalScore);
        }
    }

    private String determineMbtiType(Map<String, Double> scores) {
        // midpoint : 5문항 * (7점 척도 중 중간값 4점) = 20
        final int MIDPOINT = 20;

        // 모두 중립 선택 시 ISFP
        return String.valueOf(scores.getOrDefault("E", 0.0) > MIDPOINT ? 'E' : 'I') +
                (scores.getOrDefault("S", 0.0) > MIDPOINT ? 'S' : 'N') +
                (scores.getOrDefault("T", 0.0) > MIDPOINT ? 'T' : 'F') +
                (scores.getOrDefault("J", 0.0) > MIDPOINT ? 'J' : 'P');
    }

    private List<String> determineSubtype(Map<String, Double> scores) {
        List<String> mainTypes = List.of("E", "S", "T", "J");
        List<Map.Entry<String, Double>> subtypeEntries = scores.entrySet().stream()
                .filter(entry -> !mainTypes.contains(entry.getKey()))
                .toList();

        if (subtypeEntries.isEmpty()) {
            // 서브 타입 점수가 아예 없거나, 모두 Integer.MIN_VALUE일 경우
            return List.of();
        }

        double maxScore = subtypeEntries.stream()
                .mapToDouble(Map.Entry::getValue)
                .max()
                .orElse(Integer.MIN_VALUE);

        // 최대 점수와 일치하는 모든 서브 타입 키를 수집
        return subtypeEntries.stream()
                .filter(entry -> entry.getValue() == maxScore)
                .map(Map.Entry::getKey)
                .toList();

    }
}
