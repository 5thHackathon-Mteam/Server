package com.example.hackathon.domain.heart.repository;

import com.example.hackathon.domain.heart.domain.Heart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Long>, HeartRepositoryCustom {
}
