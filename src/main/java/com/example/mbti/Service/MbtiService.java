package com.example.mbti.Service;

import com.example.mbti.DTO.MbtiResponse;
import com.example.mbti.Entity.Mbti;
import com.example.mbti.Repository.MbtiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor  // 생성자 주입
public class MbtiService {
    private final MbtiRepository mbtiRepository;

    public List<MbtiResponse> getAllMbti() {
        List<Mbti> mbtiList = mbtiRepository.findAllByOrderByIsSubtypeAscIdAsc();
        return mbtiList.stream()
                       .map(mbti -> new MbtiResponse(
                               mbti.getId(),
                               mbti.getImgUrl(),
                               mbti.getIsSubtype(),
                               mbti.getSummary(),
                               mbti.getType()
                       ))
                       .collect(Collectors.toList());
    }
}
