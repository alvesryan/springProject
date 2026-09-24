package com.devryan.course.repositories;

import com.devryan.course.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface       CategoryRepository extends JpaRepository<Category, Long> {
}
