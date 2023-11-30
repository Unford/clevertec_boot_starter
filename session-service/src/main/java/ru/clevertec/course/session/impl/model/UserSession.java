package ru.clevertec.course.session.impl.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

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
