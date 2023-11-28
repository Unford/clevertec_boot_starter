package ru.clevertec.course.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.course.spring.model.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByNickname(String nickname);

    Optional<User> findUserByEmail(String email);

}
