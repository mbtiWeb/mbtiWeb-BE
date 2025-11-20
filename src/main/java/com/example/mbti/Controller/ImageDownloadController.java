package com.example.mbti.Controller;

import com.example.mbti.Service.ImageDownloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/download")
@RequiredArgsConstructor
public class ImageDownloadController {
    private final ImageDownloadService imageDownloadService;

    @GetMapping()
    public ResponseEntity<byte[]> getImage(
            @RequestParam("type") String type,
            @RequestParam("token") String token) {

        byte[] imageBytes = imageDownloadService.getImage(type);
        String filename = type + ".png";

        return ResponseEntity.ok()
                             .contentType(MediaType.IMAGE_PNG)
                             .header("X-Token", token)
                             // 브라우저가 해당 이미지를 저장하도록 함.
                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                             .body(imageBytes);
    }
}
