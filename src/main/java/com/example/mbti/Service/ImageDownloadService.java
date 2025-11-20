package com.example.mbti.Service;

import com.example.mbti.Entity.Mbti;
import com.example.mbti.Repository.MbtiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageDownloadService {
    private final PdfGenerationService pdfGenerationService;
    private final Pdf2ImageService pdf2ImageService;

    private final MbtiRepository mbtiRepository;

    public byte[] getImage(String type) {
        Mbti mbti = mbtiRepository.findByType(type);
        byte[] pdfBytes = pdfGenerationService.getPdf(mbti);
        return pdf2ImageService.getImage(pdfBytes);
    }
}
