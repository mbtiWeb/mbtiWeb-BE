package com.example.mbti.Repository;

import com.example.mbti.Entity.Mbti;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MbtiRepository extends JpaRepository<Mbti, Long> {
    List<Mbti> findAllByOrderByIsSubtypeAscIdAsc();
    // 이름을 기준으로 MBTI Entity를 조회
    Optional<Mbti> findByType(String type);
}
