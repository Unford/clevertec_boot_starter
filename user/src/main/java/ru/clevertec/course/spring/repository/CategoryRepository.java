package ru.clevertec.course.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.course.spring.model.domain.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
   Optional<Category> findByTitle(String title);
}
