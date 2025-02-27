package com.example.aston.repository;

import com.example.aston.model.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, UUID> {
//    List<Attraction> findByAddress_City(String city);
//    List<Attraction> findByAddress_Region(String region);
//    List<Attraction> findByServices_Name(String name);
//    List<Attraction> findByNameContaining(String name);
}