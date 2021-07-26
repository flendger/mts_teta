package ru.mtsteta.courses.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mtsteta.courses.domain.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
