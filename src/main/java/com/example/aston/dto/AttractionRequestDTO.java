package com.example.aston.dto;

import com.example.aston.model.AttractionType;
import lombok.Builder;

@Builder
public record AttractionRequestDTO (
        String name,
        String description,
        AttractionType type
){
}