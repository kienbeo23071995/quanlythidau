package com.example.backend.repository;

import com.example.backend.entity.TournamentRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRegistrationRepository extends JpaRepository<TournamentRegistration, Integer> {
}