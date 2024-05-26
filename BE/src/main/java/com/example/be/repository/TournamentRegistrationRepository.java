package com.example.be.repository;

import com.example.be.entity.TournamentRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRegistrationRepository extends JpaRepository<TournamentRegistration, Integer> {
}