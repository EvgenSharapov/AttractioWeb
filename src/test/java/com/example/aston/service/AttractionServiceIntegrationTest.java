package com.example.aston.service;

import com.example.aston.dto.AddressRequestDTO;
import com.example.aston.dto.AttractionRequestDTO;
import com.example.aston.mapper.AttractionMapper;
import com.example.aston.model.Address;
import com.example.aston.model.Attraction;
import com.example.aston.model.AttractionType;
import com.example.aston.model.TicketInfo;
import com.example.aston.repository.AttractionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.example.aston.service.attraction.AttractionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Testcontainers
@SpringBootTest
public class AttractionServiceIntegrationTest extends TestContainerConfig{


    private final AttractionRepository attractionRepo;

    private final AttractionMapper attractionMapper;

    private final AttractionServiceImpl attractionService;


    @Autowired
    public AttractionServiceIntegrationTest(AttractionRepository attractionRepo,
                                            AttractionMapper attractionMapper,
                                            AttractionServiceImpl attractionService) {
        this.attractionRepo = attractionRepo;
        this.attractionMapper = attractionMapper;
        this.attractionService = attractionService;
    }


    private TicketInfo createTicketInfo() {
        TicketInfo ticketInfo = new TicketInfo();
        ticketInfo.setPrice(new BigDecimal("303.6"));
        ticketInfo.setCurrency("руб");
        ticketInfo.setAvailability(true);
        return ticketInfo;
    }

    private Attraction createAttraction() {
        Attraction attraction = new Attraction();
        attraction.setName("Test Attraction");
        attraction.setDescription("Test Description");
        attraction.setType(AttractionType.PARK);

        TicketInfo ticketInfo = createTicketInfo();
        attraction.setTicket(ticketInfo);

        return attraction;
    }


    @Test
    public void testFindById() {
        Attraction attraction = createAttraction();
        attraction = attractionRepo.save(attraction);

        AttractionRequestDTO expectedDTO = attractionMapper.mapToAttractionRequestDTO(attraction);

        AttractionRequestDTO result = attractionService.findById(attraction.getId());

        assertNotNull(result);
        assertEquals(expectedDTO, result);
    }

    @Test
    public void testFindByIdNotFound() {
        UUID id = UUID.randomUUID();

        Exception exception = assertThrows(RuntimeException.class, () -> attractionService.findById(id));
        assertEquals("Attraction not found by id: " + id, exception.getMessage());
    }

    @Test
    public void testGetAll() {
        List<Attraction> attractions = List.of(createAttraction(), createAttraction());

        List<AttractionRequestDTO> expectedDTOs = attractionMapper.mapToAttractionRequestDTO(attractions);
        List<AttractionRequestDTO> result = attractionService.getAll();

        assertNotNull(result);
        assertEquals(expectedDTOs.size(), result.size());

    }

    @Test
    public void testSave() {
        Attraction attraction = createAttraction();
        attraction = attractionRepo.save(attraction);

//        AttractionRequestDTO expectedDTO = attractionMapper.mapToAttractionRequestDTO(attraction);
//        AttractionRequestDTO result = attractionService.save(attraction);
//
//        assertNotNull(result);
//        assertEquals(attraction.getName(), result.name());
//        assertEquals(attraction.getType(), result.type());
//        assertEquals(attraction.getDescription(), result.description());
//        assertEquals(attraction.getTicket(), result.ticketInfo());

//        assertThat(expectedDTO).isEqualToComparingFieldByField(result);
//        assertThat(result).usingRecursiveComparison().isEqualTo(expectedDTO);

//        verify(attractionRepo, times(1)).save(attraction);
//        verify(attractionMapper, times(1)).mapToAttractionRequestDTO(attraction);
    }

    @Test
    public void testDelete() {

        Attraction attraction = createAttraction();
        attractionService.save(attraction);

        attractionService.delete(attraction.getId());

        assertFalse(attractionRepo.findById(attraction.getId()).isPresent());
    }
}