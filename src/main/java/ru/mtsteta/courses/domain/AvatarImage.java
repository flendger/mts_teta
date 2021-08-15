package ru.mtsteta.courses.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "avatar_images")
@Getter
@Setter
public class AvatarImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "filename")
    private String filename;

    @OneToOne
    private User user;
}
