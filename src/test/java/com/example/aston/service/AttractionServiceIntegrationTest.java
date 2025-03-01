package com.example.aston.service;

import com.example.aston.dto.AttractionRequestDTO;
import com.example.aston.mapper.AttractionMapper;
import com.example.aston.model.Attraction;
import com.example.aston.model.AttractionType;
import com.example.aston.model.TicketInfo;
import com.example.aston.repository.AttractionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.example.aston.service.attraction.AttractionServiceImpl;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = AttractionServiceIntegrationTest.Initializer.class)
public class AttractionServiceIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test_db")
            .withUsername("test")
            .withPassword("test");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgres.getJdbcUrl(),
                    "spring.datasource.username=" + postgres.getUsername(),
                    "spring.datasource.password=" + postgres.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Mock
    private AttractionRepository attractionRepo;

    @Mock
    private AttractionMapper attractionMapper;

    @InjectMocks
    private AttractionServiceImpl attractionService;

    // Метод для создания TicketInfo
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
        attraction.setTicket(createTicketInfo());
        return attraction;
    }


    @Test
    public void testFindById() {
        UUID id = UUID.randomUUID();
        Attraction attraction = new Attraction();

        TicketInfo ticketInfo = createTicketInfo();

        AttractionRequestDTO expectedDTO = new AttractionRequestDTO(
                "Test Attraction",
                "Test Description",
                AttractionType.PARK,
                ticketInfo
        );

        when(attractionRepo.findById(id)).thenReturn(Optional.of(attraction));
        when(attractionMapper.mapToAttractionRequestDTO(attraction)).thenReturn(expectedDTO);

        AttractionRequestDTO result = attractionService.findById(id);

        assertNotNull(result);
        assertEquals(expectedDTO, result);
        verify(attractionRepo, times(1)).findById(id);
        verify(attractionMapper, times(1)).mapToAttractionRequestDTO(attraction);
    }

    @Test
    public void testFindByIdNotFound() {
        UUID id = UUID.randomUUID();

        when(attractionRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> attractionService.findById(id));
        verify(attractionRepo, times(1)).findById(id);
    }

    @Test
    public void testGetAll() {
        List<Attraction> attractions = List.of(new Attraction(), new Attraction());

        TicketInfo ticketInfo = createTicketInfo();

        List<AttractionRequestDTO> expectedDTOs = List.of(
                new AttractionRequestDTO("Attraction 1", "Description 1", AttractionType.PARK, ticketInfo),
                new AttractionRequestDTO("Attraction 2", "Description 2", AttractionType.GALLERY, ticketInfo)
        );

        when(attractionRepo.findAll()).thenReturn(attractions);
        when(attractionMapper.mapToAttractionRequestDTO(attractions)).thenReturn(expectedDTOs);

        List<AttractionRequestDTO> result = attractionService.getAll();

        assertNotNull(result);
        assertEquals(expectedDTOs.size(), result.size());
        verify(attractionRepo, times(1)).findAll();
        verify(attractionMapper, times(1)).mapToAttractionRequestDTO(attractions);
    }

    @Test
    public void testSave() {
        Attraction attraction = new Attraction();

        TicketInfo ticketInfo = createTicketInfo();

        AttractionRequestDTO expectedDTO = new AttractionRequestDTO(
                "Test Attraction",
                "Test Description",
                AttractionType.PARK,
                ticketInfo
        );

        when(attractionRepo.save(attraction)).thenReturn(attraction);
        when(attractionMapper.mapToAttractionRequestDTO(attraction)).thenReturn(expectedDTO);

        AttractionRequestDTO result = attractionService.save(attraction);

        assertNotNull(result);
        assertEquals(expectedDTO, result);
        verify(attractionRepo, times(1)).save(attraction);
        verify(attractionMapper, times(1)).mapToAttractionRequestDTO(attraction);
    }

    @Test
    public void testDelete() {
        UUID id = UUID.randomUUID();

        doNothing().when(attractionRepo).deleteById(id);

        attractionService.delete(id);

        verify(attractionRepo, times(1)).deleteById(id);
    }
}