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

@RequestMapping("/api/mbti")
@RequiredArgsConstructor
@RestController
public class MbtiResultController {
    private final ResultCalcService resultCalcService;

    @PostMapping("/result")
    public ResponseEntity<MbtiResultResponse> calculateResult(@RequestBody @Valid AnswerRequest request) {
        MbtiResultResponse response = resultCalcService.calculateMbtiType(request);
        return ResponseEntity.ok(response);
    }
}
