package com.example.mbti.Service;

import com.example.mbti.Entity.Mbti;
import com.example.mbti.Repository.MbtiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor  // 생성자 주입
public class MbtiService {
    private final MbtiRepository mbtiRepository;

    public List<Mbti> getAllMbti() {
        return mbtiRepository.findAllByOrderByIsSubtypeAscIdAsc();
    }
}
