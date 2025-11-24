package com.example.mbti.Controller;

import com.example.mbti.DTO.AnswerRequest;
import com.example.mbti.DTO.MbtiResultResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.mbti.Service.ResultCalcService;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/api/mbti")
@RequiredArgsConstructor
@RestController
@Tag(name = "MBTI 결과 계산", description = "사용자의 답변을 기반으로 MBTI 유형을 도출합니다.")
public class MbtiResultController {
    private final ResultCalcService resultCalcService;

    @Operation(summary = "MBTI 유형 계산 및 결과 반환", description = "사용자 답변 리스트(AnswerRequest)를 받아 최종 MBTI 유형과 점수를 계산합니다.")
    @PostMapping("/result")
    public ResponseEntity<MbtiResultResponse> calculateResult(@RequestBody @Valid AnswerRequest request) {
        MbtiResultResponse response = resultCalcService.calculateMbtiType(request);
        return ResponseEntity.ok(response);
    }
}
