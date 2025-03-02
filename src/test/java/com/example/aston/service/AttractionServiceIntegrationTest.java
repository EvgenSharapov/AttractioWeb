package com.example.aston.service;

import com.example.aston.dto.AttractionRequestDTO;
import com.example.aston.handler.exeptions.AttractionNotFoundException;
import com.example.aston.mapper.AttractionMapper;
import com.example.aston.model.Address;
import com.example.aston.model.Attraction;
import com.example.aston.model.AttractionType;
import com.example.aston.model.TicketInfo;
import com.example.aston.repository.AttractionRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.aston.service.attraction.AttractionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class AttractionServiceIntegrationTest{

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test_db")
            .withUsername("test")
            .withPassword("test");

    @BeforeAll
    static void startContainer() {
        postgres.start();
        System.out.println("Using database URL: " + postgres.getJdbcUrl());
    }



    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

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

    @BeforeEach
    void setUp() {
        attractionRepo.deleteAll();
    }


    private TicketInfo createTicketInfo() {
        TicketInfo ticketInfo = new TicketInfo();
        ticketInfo.setPrice(new BigDecimal("303.6"));
        ticketInfo.setCurrency("руб");
        ticketInfo.setAvailability(true);

        return ticketInfo;
    }

    private Address createAddress(){
        Address address = new Address();
        address.setCity("Moscow");
        address.setStreet("Test Street");
        address.setRegion("Moscow reg");
        address.setBuilding(123);
        return address;

    }

    private Attraction createAttraction() {
        Attraction attraction = new Attraction();
        attraction.setName("Test Attraction");
        attraction.setDescription("Test Description");
        attraction.setType(AttractionType.PARK);

        Address address = createAddress();
        attraction.setAddress(address);

        TicketInfo ticketInfo = createTicketInfo();
        ticketInfo.setAttraction(attraction);
        attraction.setTicket(ticketInfo);

        return attraction;
    }


    @Test
    public void findById_ShouldReturnAttractionRequestDTO_WhenAttractionExists() {
        Attraction attraction = createAttraction();
        attraction = attractionRepo.save(attraction);

        AttractionRequestDTO expectedDTO = attractionMapper.mapToAttractionRequestDTO(attraction);

        AttractionRequestDTO result = attractionService.findById(attraction.getId());

        assertNotNull(result);
        assertThat(expectedDTO).isEqualToComparingFieldByField(result);
    }

    @Test
    public void findById_ShouldThrowRuntimeException_WhenAttractionDoesNotExist() {
        UUID id = UUID.randomUUID();

        Exception exception = assertThrows(AttractionNotFoundException.class, () -> attractionService.findById(id));
        assertEquals("Attraction not found by id: " + id, exception.getMessage());
    }

    @Test
    public void getAll_ShouldReturnListOfAttractionRequestDTO_WhenAttractionExist() {
        List<Attraction> attractions = List.of(createAttraction(), createAttraction(),createAttraction());

        attractionRepo.saveAll(attractions);

        List<AttractionRequestDTO> expectedDTOs = attractionMapper.mapToAttractionRequestDTO(attractions);
        List<AttractionRequestDTO> result = attractionService.getAll();

        assertNotNull(result);
        assertEquals(expectedDTOs.size(), result.size());

    }

    @Test
    public void save_ShouldReturnAttractionRequestDTO_WhenAttractionIsSaved() {
        Attraction attraction = createAttraction();
        attraction = attractionRepo.save(attraction);

        AttractionRequestDTO expectedDTO = attractionMapper.mapToAttractionRequestDTO(attraction);
        AttractionRequestDTO result = attractionService.save(attraction);

        assertNotNull(result);
        assertThat(expectedDTO).isEqualToComparingFieldByField(result);
    }

    @Test
    public void delete_ShouldDeleteAttraction_WhenAttractionExists() {

        Attraction attraction = createAttraction();
        attractionService.save(attraction);

        attractionService.delete(attraction.getId());

        assertFalse(attractionRepo.findById(attraction.getId()).isPresent());
    }
}