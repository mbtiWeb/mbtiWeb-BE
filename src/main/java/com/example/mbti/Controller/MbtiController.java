package com.example.mbti.Controller;

import com.example.mbti.DTO.MbtiDetailResponse;
import com.example.mbti.DTO.MbtiResponse;
import com.example.mbti.Entity.Mbti;
import com.example.mbti.Service.MbtiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        List<MbtiResponse> mbtiList = mbtiService.getAllMbti();
        return ResponseEntity.ok(mbtiList);
    }
    @Operation(
            summary = "MBTI 이름으로 상세 조회",
            description = "MBTI 이름을 입력받아 해당 MBTI의 전체 정보를 반환"
    )
    @GetMapping("/{mbtiName}")
    public ResponseEntity<MbtiDetailResponse> getMbtiByName(
            @Parameter(description = "조회할 MBTI 이름 (대소문자 구분 없음)", required = true)
            @PathVariable("mbtiName") String mbtiName) {

        MbtiDetailResponse mbtiData = mbtiService.getMbtiByName(mbtiName);
        return ResponseEntity.ok(mbtiData);
    }
}
