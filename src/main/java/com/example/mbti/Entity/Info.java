package com.example.mbti.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="info")
@Setter
@Getter
@NoArgsConstructor
public class Info {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="type", nullable=false)
    private String type;

    @Column(name="subtype", nullable=false, unique=true)
    private String subtype;

    @Lob
    @Column(name="instruction", nullable=false)
    private String instruction;
}
