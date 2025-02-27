package com.example.aston.mapper;

import com.example.aston.dto.TravelServiceRequestDTO;
import com.example.aston.model.TravelService;
import com.example.aston.model.ServiceType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TravelServiceMapperTest {

    @InjectMocks
    private TravelServiceMapper attractionServiceMapper;

    @Test
    void mapToAttractionServiceRequestDTO_ShouldMapSingleServiceCorrectly() {
        TravelService service = new TravelService();
        service.setName("Test name");
        service.setDescription("Test description");
        service.setType(ServiceType.PHOTOGRAPHY_SERVICE);

        TravelServiceRequestDTO result = attractionServiceMapper.mapToTravelServiceRequestDTO(service);

        assertNotNull(result);
        assertEquals("Test name", result.name());
        assertEquals("Test description", result.description());
        assertEquals(ServiceType.PHOTOGRAPHY_SERVICE, result.type());
    }

    @Test
    void mapToAttractionServiceRequestDTO_ShouldMapListOfServicesCorrectly() {
        TravelService service = new TravelService();
        service.setName("Test name");
        service.setDescription("Test description");
        service.setType(ServiceType.PHOTOGRAPHY_SERVICE);

        List<TravelService> services = Collections.singletonList(service);

        List<TravelServiceRequestDTO> result = attractionServiceMapper.mapToTravelServiceRequestDTO(services);

        assertNotNull(result);
        assertEquals(1, result.size());

        TravelServiceRequestDTO dto = result.get(0);
        assertEquals("Test name", dto.name());
        assertEquals("Test description", dto.description());
        assertEquals(ServiceType.PHOTOGRAPHY_SERVICE, dto.type());
    }

    @Test
    void mapToAttractionServiceRequestDTO_ShouldReturnEmptyList_WhenInputListIsEmpty() {
        List<TravelService> services = Collections.emptyList();

        List<TravelServiceRequestDTO> result = attractionServiceMapper.mapToTravelServiceRequestDTO(services);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}