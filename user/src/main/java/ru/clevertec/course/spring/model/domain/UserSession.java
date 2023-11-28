package ru.clevertec.course.spring.model.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;
import ru.clevertec.course.session.api.model.SessionDetails;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "user_sessions")
public class UserSession {

    @Id
    @UuidGenerator
    private String id;
    private String login;
    private LocalDateTime createDateTime;

    @PrePersist
    public void onPrePersist() {
        this.createDateTime = LocalDateTime.now();
    }
}
