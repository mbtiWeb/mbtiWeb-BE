package com.example.mbti.Controller;

import com.example.mbti.Entity.Mbti;
import com.example.mbti.Service.MbtiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mbti")
@Tag(name="MBTI API", description="MBTI 관련 기능")
@RequiredArgsConstructor  // 생성자 주입
public class MbtiController {
    private final MbtiService mbtiService;

    @Operation(
            summary = "MBTI 전체 조회",
            description = "DB에 저장된 모든 MBTI 데이터 반환"
    )
    @GetMapping("/all")
    public ResponseEntity<?> getAllMbti() {
        List<Mbti> mbtiList = mbtiService.getAllMbti();
        return ResponseEntity.ok(mbtiList);
    }
}
