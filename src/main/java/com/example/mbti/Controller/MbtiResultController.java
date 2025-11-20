package com.example.mbti.Controller;

import com.example.mbti.DTO.AnswerRequest;
import com.example.mbti.DTO.MbtiResultResponse;
import com.example.mbti.Service.ResultCalcService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController // JSON/XML 응답을 자동으로 처리 (Controller + @ResponseBody)
@RequestMapping("/api/mbti")
@RequiredArgsConstructor

@Controller
public class MbtiResultController {
    private final ResultCalcService resultCalcService;

    @PostMapping("/result")
    public ResponseEntity<MbtiResultResponse> calculateResult(@RequestBody @Valid AnswerRequest request) {
        MbtiResultResponse response = resultCalcService.calculateMbtiType(request);
        return ResponseEntity.ok(response);
    }
}
