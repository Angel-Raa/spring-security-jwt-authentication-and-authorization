package com.github.angel.raa.modules.persistence.repository;

import com.github.angel.raa.modules.persistence.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> { }
