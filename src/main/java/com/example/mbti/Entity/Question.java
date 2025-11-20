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
    @Column(name="id", nullable=false, unique=true)
    private Long id;  // 자동 생성되는 id( PK )
    
    @Column(name="number", nullable=false, unique=true)
    private Integer number;  // 질문 번호

    @Column(name="is_reversed", nullable=false)
    private Boolean is_reversed;  // E <-> I

    @Column(name="question", nullable=false, unique=true)
    private String question;  // 질문
}
