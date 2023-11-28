package ru.clevertec.course.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.course.spring.model.domain.Channel;
import ru.clevertec.course.spring.model.domain.projection.ChannelTitleOnly;
import ru.clevertec.course.spring.model.domain.projection.ChannelWithSubCount;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
    Optional<Channel> findChannelByTitle(String title);

    @Query(""" 
            SELECT  c.title as title, c.image as image, cat.title AS category,
                    c.language as language, COUNT(ucs) AS subscriberCount
                     FROM Channel c
                     LEFT JOIN c.category cat
                     LEFT JOIN  c.subscribers ucs
                     WHERE (:title IS NULL OR c.title ILIKE %:title%)
                     AND (:language IS NULL OR c.language LIKE %:language%)
                     AND (:category IS NULL OR cat.title LIKE %:category%)
                     GROUP BY c.id, cat.id
            """)
    Page<ChannelWithSubCount> findAllFiltered(String title, String language, String category, Pageable pageable);

    List<ChannelTitleOnly> findBySubscribersId(Long id);


}
