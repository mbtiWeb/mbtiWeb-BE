package com.example.mbti.Service;

import com.example.mbti.DTO.QuestionResponseItem;
import com.example.mbti.Entity.Question;
import com.example.mbti.Repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import java.security.SecureRandom;
import java.util.Base64;
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
}
