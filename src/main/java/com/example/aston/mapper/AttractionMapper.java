package com.example.aston.mapper;

import com.example.aston.dto.AttractionRequestDTO;
import com.example.aston.model.Attraction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AttractionMapper {

    public AttractionRequestDTO mapToAttractionRequestDTO(Attraction attraction) {
        return AttractionRequestDTO.builder()
                .name(attraction.getName())
                .description(attraction.getDescription())
                .type(attraction.getType())
                .ticketInfo(attraction.getTicket())
                .address(attraction.getAddress())
                .build();
    }

    public List<AttractionRequestDTO> mapToAttractionRequestDTO(List<Attraction> attractions) {
        return attractions.stream()
                .map(this::mapToAttractionRequestDTO)
                .collect(Collectors.toList());
    }
}
