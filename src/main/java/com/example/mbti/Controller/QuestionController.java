package com.example.mbti.Controller;

import com.example.mbti.DTO.QuestionResponse;
import com.example.mbti.DTO.QuestionResponseItem;
import com.example.mbti.Entity.Question;
import com.example.mbti.Service.QuestionService;
import com.example.mbti.Service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question")
@Tag(name="Question API", description="MBTI 문항 관련 기능")
@RequiredArgsConstructor  // 생성자 주입
public class QuestionController {
    private final QuestionService questionService;
    private final TokenService tokenService;

    @Operation(
            summary="MBTI 문항 전체 조회",
            description="'MBTI 모든 문항'과 '사용자를 구분할 수 있는 토큰' 반환"
    )
    @GetMapping("/all")
    public ResponseEntity<?> getAllQuestions() {
        List<QuestionResponseItem> questionList = questionService.getAllQuestions();
        String token = tokenService.createToken();

        return ResponseEntity.ok(new QuestionResponse(questionList, token));
    }
}
