package com.example.mbti.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MbtiResponse {
    private Long id;
    private String img_url;
    private Boolean is_subtype;
    private String summary;
    private String type;
}
