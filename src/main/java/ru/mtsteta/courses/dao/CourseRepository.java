package ru.mtsteta.courses.dao;

import ru.mtsteta.courses.domain.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    List<Course> findAll();

    Optional<Course> findById(long id);

    Course save(Course course);

    void delete(long id);

    List<Course> findByTitleWithPrefix(String prefix);
}
