package ru.clevertec.course.spring.model.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String title;
    private String description;
    private String image;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToMany(mappedBy = "subscribedChannels")
    @ToString.Exclude
    private Set<User> subscribers;

    private LocalDate createDate;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Category category;
    private String language;

    @PrePersist
    public void onPrePersist() {
        this.createDate = LocalDate.now();
    }


}
