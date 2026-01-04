package com.example.mbti.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="mbti")
@Setter
@Getter
@NoArgsConstructor
public class Mbti {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;  // 자동 생성되는 id( PK )

    @Column(name="type", nullable=false, unique=true)
    private String type;  // mbti + subtype 16 + 12 = 28개

    @Column(name="is_subtype", nullable=false)
    private Boolean isSubtype;  // subtype이면 True

    @Column(name="summary", nullable=false)
    private String summary;  // 한 줄 소개

    @Lob
    @Column(name="instruction", nullable=false, columnDefinition="LONGTEXT")
    private String instruction;  // 설명

    @Column(name="img_url", nullable=false, unique=true)
    private String imgUrl;  // 캐릭터 url
}
