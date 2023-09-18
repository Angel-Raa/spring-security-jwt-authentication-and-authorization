package com.github.angel.raa.modules.persistence.repository;

import com.github.angel.raa.modules.persistence.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> { }
