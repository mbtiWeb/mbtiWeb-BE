package com.example.mbti.Service;

import com.example.mbti.Entity.Mbti;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PdfGenerationService {
    public byte[] getPdf(Mbti mbti) {
        return new byte[0];
    }
}
