package ru.clevertec.course.session.impl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.course.session.impl.model.UserSession;
import ru.clevertec.course.session.api.model.SessionDetails;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    Optional<SessionDetails> findByLogin(String login);


    @Modifying
    @Query("DELETE FROM UserSession u WHERE u.createDateTime < :startOfDay")
    int deleteByCreatedDateTimeLessThan(LocalDateTime startOfDay);
}