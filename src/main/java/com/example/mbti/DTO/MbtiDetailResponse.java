package com.example.mbti.DTO;

import com.example.mbti.Entity.Mbti;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MbtiDetailResponse {
    private Long id;
    private String img_url;
    private Boolean is_subtype;
    private String summary;
    private String instruction;
    private String type;

    public static MbtiDetailResponse of(Mbti mbtiEntity) {
        return new MbtiDetailResponse(
                mbtiEntity.getId(),
                mbtiEntity.getImgUrl(),
                mbtiEntity.getIsSubtype(),
                mbtiEntity.getSummary(),
                mbtiEntity.getInstruction(),
                mbtiEntity.getType()
        );
    }
}
