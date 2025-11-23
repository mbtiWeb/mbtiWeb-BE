package com.example.mbti.Service;

import com.example.mbti.DTO.AnswerRequest;
import com.example.mbti.DTO.AnswerRequestItem;
import com.example.mbti.DTO.MbtiResultResponse;
import com.example.mbti.Entity.Mbti;
import com.example.mbti.Entity.Question;
import com.example.mbti.Service.QuestionService;

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
        Map<String, Integer> scores = new HashMap<>();

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

    public void processAnswer(AnswerRequestItem item, Map<String, Integer> scores, Question question) {
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
            int currentTotalScore = scores.getOrDefault(type, 0);
            scores.put(type, currentTotalScore + finalScore);
        }
    }

    private String determineMbtiType(Map<String, Integer> scores) {
        // midpoint : 6문항 * (7점 척도 중 중간값 4점) = 24
        final int MIDPOINT = 24;

        return String.valueOf(scores.getOrDefault("E", 0) > MIDPOINT ? 'E' : 'I') +
                (scores.getOrDefault("S", 0) > MIDPOINT ? 'S' : 'N') +
                (scores.getOrDefault("T", 0) > MIDPOINT ? 'T' : 'F') +
                (scores.getOrDefault("J", 0) > MIDPOINT ? 'J' : 'P');
    }

    private List<String> determineSubtype(Map<String, Integer> scores) {
        List<String> mainTypes = List.of("E", "S", "T", "J");
        List<Map.Entry<String, Integer>> subtypeEntries = scores.entrySet().stream()
                .filter(entry -> !mainTypes.contains(entry.getKey()))
                .toList();

        if (subtypeEntries.isEmpty()) {
            // 서브 타입 점수가 아예 없거나, 모두 Integer.MIN_VALUE일 경우
            return List.of();
        }

        int maxScore = subtypeEntries.stream()
                .mapToInt(Map.Entry::getValue)
                .max()
                .orElse(Integer.MIN_VALUE);

        // 최대 점수와 일치하는 모든 서브 타입 키를 수집
        return subtypeEntries.stream()
                .filter(entry -> entry.getValue() == maxScore)
                .map(Map.Entry::getKey)
                .toList();

    }
}
