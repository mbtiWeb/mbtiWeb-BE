package com.example.mbti.Service;

import com.example.mbti.Entity.Question;
import com.example.mbti.Repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor  // 생성자 주입
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Question> getAllQuestions() {
        return questionRepository.findAllByOrderByNumberAsc();
    }

    // 결과 계산 로직에서 문항 정보 조회
    public Question getQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException("ID " + id + "에 해당하는 질문 데이터를 찾을 수 없습니다.")
                );
    }
}
