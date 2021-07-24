package ru.mtsteta.courses.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mtsteta.courses.domain.Course;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTitleLike(String title);

    List<Course> findByAuthorLike(String author);
}
