package ru.clevertec.course.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.course.spring.model.domain.UserSession;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSession, String> {
    Optional<UserSession> findByLogin(String login);
    @Modifying
    @Query("DELETE FROM UserSession u WHERE u.createDateTime < :startOfDay")
    int deleteByCreatedDateTimeLessThan(LocalDateTime startOfDay);
}