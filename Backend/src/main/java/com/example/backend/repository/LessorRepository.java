package com.example.backend.repository;

import com.example.backend.entity.Lessor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessorRepository extends JpaRepository<Lessor, Integer> {
}