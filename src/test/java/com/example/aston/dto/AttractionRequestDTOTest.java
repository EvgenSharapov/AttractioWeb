package com.example.aston.dto;

import com.example.aston.model.AttractionType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AttractionRequestDTOTest {

    @Test
    public void testAttractionRequestDTO() {

        AttractionRequestDTO attraction = AttractionRequestDTO.builder()
                .name("Attraction")
                .description("Description")
                .type(AttractionType.GALLERY)
                .build();


        assertEquals("Attraction", attraction.name());
        assertEquals("Description", attraction.description());
        assertEquals(AttractionType.GALLERY, attraction.type());
    }
}