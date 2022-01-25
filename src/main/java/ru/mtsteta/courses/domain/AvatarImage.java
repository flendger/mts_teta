package ru.mtsteta.courses.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "avatar_images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvatarImage {
    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "filename")
    private String filename;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
