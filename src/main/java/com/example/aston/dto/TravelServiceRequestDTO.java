package com.example.aston.dto;

import com.example.aston.model.Attraction;
import com.example.aston.model.ServiceType;
import lombok.Builder;

import java.util.Set;

@Builder
public record TravelServiceRequestDTO (
        String name,
        String description,
        ServiceType type,
        Set<Attraction> attractions
){
}