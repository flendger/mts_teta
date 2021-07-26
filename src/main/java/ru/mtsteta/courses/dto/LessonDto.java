package ru.mtsteta.courses.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mtsteta.courses.domain.Lesson;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class LessonDto {
    private Long id;

    @NotBlank(message = "Название урока должно быть заполнено")
    private String title;

    private String text;
    private Long courseId;

    public LessonDto(Long courseId) {
        this.courseId = courseId;
    }

    public static LessonDto from(Lesson lesson) {
        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(lesson.getId());
        lessonDto.setTitle(lesson.getTitle());
        lessonDto.setText(lesson.getText());
        lessonDto.setCourseId(lesson.getCourse().getId());

        return lessonDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LessonDto lesson = (LessonDto) o;

        return id.equals(lesson.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
