package com.example.mbti.Service;

import com.example.mbti.DTO.QuestionResponseItem;
import com.example.mbti.Entity.Question;
import com.example.mbti.Repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor  // 생성자 주입
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<QuestionResponseItem> getAllQuestions() {
        List<Question> questionList = questionRepository.findAllByOrderByNumberAsc();
        return questionList.stream()
                           .map(question -> new QuestionResponseItem(
                                   question.getNumber(),
                                   question.getIsReversed(),
                                   question.getQuestion()
                           ))
                           .collect(Collectors.toList());
    }

    public Map<Integer, Question> getAllQuestionsAsMap() {
        List<Question> questionList = questionRepository.findAllByOrderByNumberAsc();

        // 로드된 질문이 전혀 없을 경우 예외 발생
        if (questionList.isEmpty()) {
            throw new NoSuchElementException("테스트 문항 데이터가 데이터베이스에 존재하지 않습니다. DB 연결 및 question 테이블 데이터를 확인하십시오.");
        }

        return questionList.stream()
                .collect(Collectors.toMap(
                        Question::getNumber,      // question -> question.getNumber()와 동일
                        question -> question,
                        (existing, replacement) -> existing));
    }
}
