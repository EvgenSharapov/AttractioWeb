package com.example.aston.dto;

import com.example.aston.model.ServiceType;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class TravelServiceRequestDTOTest {

    @Test
    public void testAttractionServiceRequestDTO() {

        TravelServiceRequestDTO service = TravelServiceRequestDTO.builder()
                .name("Service")
                .description("Description")
                .type(ServiceType.RENTAL_SERVICE)
                .build();


        assertEquals("Service", service.name());
        assertEquals("Description", service.description());
        assertEquals(ServiceType.RENTAL_SERVICE, service.type());
    }
}