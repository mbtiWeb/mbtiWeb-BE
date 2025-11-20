package com.example.mbti.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Pdf2ImageService {
    public byte[] getImage(byte[] pdfBytes) {
        return new byte[0];
    }
}
