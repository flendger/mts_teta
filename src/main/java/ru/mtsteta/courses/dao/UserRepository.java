package ru.mtsteta.courses.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mtsteta.courses.domain.Course;
import ru.mtsteta.courses.domain.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByCoursesNotContains(Course course);
}
