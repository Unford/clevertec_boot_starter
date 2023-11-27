package ru.clevertec.course.session.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.course.session.api.model.domain.UserSession;
import ru.clevertec.course.session.starter.model.SessionDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    Optional<SessionDetails> findByLogin(String login);


    @Modifying
    @Query("DELETE FROM UserSession u WHERE u.createDateTime < :startOfDay")
    int deleteByCreatedDateTimeLessThan(LocalDateTime startOfDay);
}
