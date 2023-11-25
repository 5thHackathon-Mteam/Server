package com.example.hackathon.domain.feed.repository;

import com.example.hackathon.domain.feed.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
