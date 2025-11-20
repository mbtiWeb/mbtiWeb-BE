package com.example.mbti.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="questions")
@Setter
@Getter
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;  // 자동 생성되는 id( PK )
    
    @Column(name="number", nullable=false, unique=true)
    private Integer number;  // 질문 번호

    @Column(name="about_subtype")
    private Boolean aboutSubtype;  // subtype과 관련된 질문인가?

    @Column(name="type")
    private String type;  // 어떤 type과 관련된 질문인가?

    @Column(name="is_reversed", nullable=false)
    private Boolean isReversed;  // E <-> I

    @Column(name="question", nullable=false, unique=true)
    private String question;  // 질문
}
