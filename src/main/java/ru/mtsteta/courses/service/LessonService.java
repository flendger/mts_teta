package ru.mtsteta.courses.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mtsteta.courses.dao.CourseRepository;
import ru.mtsteta.courses.dao.LessonRepository;
import ru.mtsteta.courses.domain.Course;
import ru.mtsteta.courses.domain.Lesson;
import ru.mtsteta.courses.dto.LessonDto;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    public Lesson save(LessonDto lessonDto) {
        Course course = courseRepository.getById(lessonDto.getCourseId());
        Lesson lesson = new Lesson();
        lesson.setId(lessonDto.getId());
        lesson.setTitle(lessonDto.getTitle());
        lesson.setText(lessonDto.getText());
        lesson.setCourse(course);
        return lessonRepository.save(lesson);
    }

    public Optional<Lesson> findById(Long id) {
        return lessonRepository.findById(id);
    }

    public void deleteById(Long id) {
        lessonRepository.deleteById(id);
    }
}
