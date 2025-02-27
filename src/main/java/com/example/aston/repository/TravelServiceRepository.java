package com.example.aston.repository;

import com.example.aston.model.TravelService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TravelServiceRepository extends JpaRepository<TravelService, UUID> {
}
