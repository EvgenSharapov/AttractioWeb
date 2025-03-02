package com.example.aston.service;

import com.example.aston.dto.AddressRequestDTO;
import com.example.aston.handler.exeptions.AddressNotFoundException;
import com.example.aston.mapper.AddressMapper;
import com.example.aston.model.Address;
import com.example.aston.repository.AddressRepository;
import com.example.aston.service.address.AddressServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;




@SpringBootTest
public class AddressServiceIntegrationTest {

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


    private final AddressRepository addressRepo;
    private final AddressMapper addressMapper;
    private final AddressServiceImpl addressService;

    @Autowired
    public AddressServiceIntegrationTest(AddressRepository addressRepo,
                                         AddressMapper addressMapper,
                                         AddressServiceImpl addressService) {
        this.addressRepo = addressRepo;
        this.addressMapper = addressMapper;
        this.addressService = addressService;
    }




    @BeforeEach
    void setUp() {

        addressRepo.deleteAll();
    }

    private Address createAddress() {
        Address address = new Address();
        address.setBuilding(745);
        address.setStreet("Angarsk street");
        address.setCity("Moscow");
        address.setRegion("Moscow region");
        return address;
    }


    @Test
    public void findById_ShouldReturnAddressRequestDTO_WhenAddressExists() {

        Address address = createAddress();
        address = addressRepo.save(address);

        AddressRequestDTO expectedDTO = addressMapper.mapToAddressRequestDTO(address);

        AddressRequestDTO result = addressService.findById(address.getId());

        assertNotNull(result);
        assertEquals(expectedDTO, result);
    }

    @Test
    public void findById_ShouldThrowRuntimeException_WhenAddressDoesNotExist() {
        UUID id = UUID.randomUUID();

        Exception exception = assertThrows(AddressNotFoundException.class, () -> addressService.findById(id));
        assertEquals("Address not found by id: " + id, exception.getMessage());
    }

    @Test
    public void getAll_ShouldReturnListOfAddressRequestDTO_WhenAddressExist() {
        Address address1 = createAddress();
        Address address2 = createAddress();
        addressRepo.saveAll(List.of(address1, address2));

        List<AddressRequestDTO> expectedDTOs = addressMapper.mapToAddressRequestDTO(List.of(address1, address2));

        List<AddressRequestDTO> result = addressService.getAll();

        assertNotNull(result);
        assertEquals(expectedDTOs.size(), result.size());
    }

    @Test
    public void save_ShouldReturnAddressRequestDTO_WhenAddressIsSaved() {
        Address address = createAddress();

        AddressRequestDTO result = addressService.save(address);

        assertNotNull(result);
        assertEquals(address.getStreet(), result.street());
        assertEquals(address.getCity(), result.city());
    }

    @Test
    public void delete_ShouldDeleteAddress_WhenAddressExists() {
        Address address = createAddress();
        address = addressRepo.save(address);

        addressService.delete(address.getId());

        assertFalse(addressRepo.findById(address.getId()).isPresent());
    }
}