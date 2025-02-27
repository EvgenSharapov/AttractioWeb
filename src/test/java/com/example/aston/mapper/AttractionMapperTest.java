package com.example.aston.mapper;

import com.example.aston.dto.AttractionRequestDTO;
import com.example.aston.model.Attraction;
import com.example.aston.model.AttractionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AttractionMapperTest {

    @InjectMocks
    private AttractionMapper attractionMapper;

    @Test
    void mapToAttractionRequestDTO_ShouldMapSingleAttractionCorrectly() {
        Attraction attraction = new Attraction();
        attraction.setName("Test name");
        attraction.setDescription("Test description");
        attraction.setType(AttractionType.GALLERY);

        AttractionRequestDTO result = attractionMapper.mapToAttractionRequestDTO(attraction);

        assertNotNull(result);
        assertEquals("Test name", result.name());
        assertEquals("Test description", result.description());
        assertEquals(AttractionType.GALLERY, result.type());
    }

    @Test
    void mapToAttractionRequestDTO_ShouldMapListOfAttractionsCorrectly() {
        Attraction attraction = new Attraction();
        attraction.setName("Test name");
        attraction.setDescription("Test description");
        attraction.setType(AttractionType.GALLERY);

        List<Attraction> attractions = Collections.singletonList(attraction);

        List<AttractionRequestDTO> result = attractionMapper.mapToAttractionRequestDTO(attractions);

        assertNotNull(result);
        assertEquals(1, result.size());

        AttractionRequestDTO dto = result.get(0);
        assertEquals("Test name", dto.name());
        assertEquals("Test description", dto.description());
        assertEquals(AttractionType.GALLERY, dto.type());
    }

    @Test
    void mapToAttractionRequestDTO_ShouldReturnEmptyList_WhenInputListIsEmpty() {
        List<Attraction> attractions = Collections.emptyList();

        List<AttractionRequestDTO> result = attractionMapper.mapToAttractionRequestDTO(attractions);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}