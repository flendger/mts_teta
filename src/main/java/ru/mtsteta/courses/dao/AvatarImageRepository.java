package ru.mtsteta.courses.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mtsteta.courses.domain.AvatarImage;

@Repository
public interface AvatarImageRepository extends JpaRepository<AvatarImage, Long> {
}
