package com.example.aston.service;

import com.example.aston.dto.TravelServiceRequestDTO;
import com.example.aston.handler.exeptions.TravelServiceNotFoundException;
import com.example.aston.mapper.TravelServiceMapper;
import com.example.aston.model.ServiceType;
import com.example.aston.model.TravelService;
import com.example.aston.repository.TravelServiceRepository;
import com.example.aston.service.travel_service.TravelServiceServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
class TravelServiceServiceTest{

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

    private final TravelServiceRepository serviceRepo;

    private final TravelServiceMapper serviceMapper;

    private final TravelServiceServiceImpl travelService;

    @Autowired
    public TravelServiceServiceTest(TravelServiceRepository serviceRepo,
                                    TravelServiceMapper serviceMapper,
                                    TravelServiceServiceImpl travelService) {
        this.serviceRepo = serviceRepo;
        this.serviceMapper = serviceMapper;
        this.travelService = travelService;
    }


    @BeforeEach
    void setUp() {
        serviceRepo.deleteAll();
    }

    private TravelService createTravelService() {
        TravelService service = new TravelService();
        service.setType(ServiceType.RENTAL_SERVICE);
        service.setDescription("Test Description");
        service.setName("Test Name");
        return service;
    }


    @Test
    void findById_ShouldReturnTravelServiceRequestDTO_WhenServiceExists() {

        TravelService service = createTravelService();
        service = serviceRepo.save(service);

        TravelServiceRequestDTO expectedDTO = serviceMapper.mapToTravelServiceRequestDTO(service);

        TravelServiceRequestDTO result = travelService.findById(service.getId());

        assertNotNull(result);
        assertEquals(expectedDTO, result);
    }

    @Test
    void findById_ShouldThrowRuntimeException_WhenServiceDoesNotExist() {
        UUID id = UUID.randomUUID();

        Exception exception = assertThrows(TravelServiceNotFoundException.class, () -> travelService.findById(id));
        assertEquals("Travel Service not found by id: " + id, exception.getMessage());

    }

    @Test
    void getAll_ShouldReturnListOfTravelServiceRequestDTO_WhenServicesExist() {
        TravelService service = createTravelService();
        TravelService service1 = createTravelService();
        serviceRepo.saveAll(List.of(service,service1));

        List<TravelServiceRequestDTO> expectedDTOs = serviceMapper.mapToTravelServiceRequestDTO(List.of(service,service1));
        List<TravelServiceRequestDTO> result = travelService.getAll();

        assertNotNull(result);
        assertEquals(expectedDTOs.size(), result.size());

    }

    @Test
    void save_ShouldReturnTravelServiceRequestDTO_WhenServiceIsSaved() {
        TravelService service = createTravelService();

        TravelServiceRequestDTO result = travelService.save(service);

        assertNotNull(result);
        assertEquals(service.getDescription(), result.description());
        assertEquals(service.getName(), result.name());
        assertEquals(service.getType(), result.type());

    }

    @Test
    void delete_ShouldDeleteService_WhenServiceExists() {
        TravelService service = createTravelService();
        service = serviceRepo.save(service);

        travelService.delete(service.getId());

        assertFalse(serviceRepo.findById(service.getId()).isPresent());

    }
}
