package ru.mtsteta.courses.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Course {
    private Long id;

    @NotBlank(message = "Автор курса должен быть заполнен")
    private String author;

    @NotBlank(message = "Название курса должно быть заполнено")
    private String title;
}
