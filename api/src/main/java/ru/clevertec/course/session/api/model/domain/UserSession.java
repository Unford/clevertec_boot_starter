package ru.clevertec.course.session.api.model.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import ru.clevertec.course.session.starter.model.SessionDetails;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "user_sessions")
public class UserSession implements SessionDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String login;
    private LocalDateTime createDateTime;

    @PrePersist
    public void onPrePersist() {
        this.createDateTime = LocalDateTime.now();
    }
}
