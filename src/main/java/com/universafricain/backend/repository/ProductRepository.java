package com.universafricain.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.universafricain.backend.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByIsAvailableTrue();

    List<Product> findByNameContainingIgnoreCase(String keyword);
}