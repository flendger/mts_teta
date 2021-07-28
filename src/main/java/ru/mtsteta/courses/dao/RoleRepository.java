package ru.mtsteta.courses.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mtsteta.courses.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
