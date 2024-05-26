package com.example.be.repository;

import com.example.be.entity.BadmintonCourt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadmintonCourtRepository extends JpaRepository<BadmintonCourt, Integer> {
}