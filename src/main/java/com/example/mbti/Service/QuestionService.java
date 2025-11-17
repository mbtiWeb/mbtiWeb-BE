package com.example.mbti.Service;

import com.example.mbti.Entity.Question;
import com.example.mbti.Repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@RequiredArgsConstructor  // 생성자 주입
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Question> getAllQuestions() {
        return questionRepository.findAllByOrderByNumberAsc();
    }
}
