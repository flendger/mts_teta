package ru.mtsteta.courses.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mtsteta.courses.dao.CourseRepository;
import ru.mtsteta.courses.dao.LessonRepository;
import ru.mtsteta.courses.domain.Course;
import ru.mtsteta.courses.domain.Lesson;
import ru.mtsteta.courses.dto.LessonDto;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    public Lesson save(LessonDto lessonDto) {
        Course course = courseRepository.getOne(lessonDto.getCourseId());
        Lesson lesson = new Lesson();
        lesson.setId(lessonDto.getId());
        lesson.setTitle(lessonDto.getTitle());
        lesson.setText(lessonDto.getText());
        lesson.setCourse(course);
        return lessonRepository.save(lesson);
    }
}
