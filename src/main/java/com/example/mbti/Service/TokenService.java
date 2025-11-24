package com.example.mbti.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@RequiredArgsConstructor  // 생성자 주입
public class TokenService {
    public String createToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];  // 256bit
        random.nextBytes(bytes);  // bytes 배열을 랜덤한 값으로 채움.
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);  // 문자열 토큰 반환
    }
}
