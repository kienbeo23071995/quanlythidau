package com.example.backend.repository;

import com.example.backend.entity.BadmintonCourt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadmintonCourtRepository extends JpaRepository<BadmintonCourt, Integer> {
}