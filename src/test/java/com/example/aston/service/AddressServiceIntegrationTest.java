package com.example.aston.service;

import com.example.aston.dto.AddressRequestDTO;
import com.example.aston.mapper.AddressMapper;
import com.example.aston.model.Address;
import com.example.aston.repository.AddressRepository;
import com.example.aston.service.address.AddressServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;



@Testcontainers
@SpringBootTest
@RequiredArgsConstructor
@ContextConfiguration(initializers = AddressServiceIntegrationTest.Initializer.class)
public class AddressServiceIntegrationTest {

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

    @MockBean
    private AddressRepository addressRepo;

    @MockBean
    private AddressMapper addressMapper;

    private AddressServiceImpl addressService;

    @BeforeEach
    void setUp() {
        addressService = new AddressServiceImpl(addressRepo, addressMapper);
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

    private AddressRequestDTO createAddressRequestDTO() {
        return AddressRequestDTO.builder()
                .building(745)
                .street("Angarsk street")
                .city("Moscow")
                .region("Moscow region")
                .build();
    }

    @Test
    public void testFindById() {
        UUID id = UUID.randomUUID();
        Address address = createAddress();
        AddressRequestDTO expectedDTO = createAddressRequestDTO();

        when(addressRepo.findById(id)).thenReturn(Optional.of(address));
        when(addressMapper.mapToAddressRequestDTO(address)).thenReturn(expectedDTO);

        AddressRequestDTO result = addressService.findById(id);

        assertNotNull(result);
        assertEquals(expectedDTO, result);
        verify(addressRepo, times(1)).findById(id);
        verify(addressMapper, times(1)).mapToAddressRequestDTO(address);
    }

    @Test
    public void testFindByIdNotFound() {
        UUID id = UUID.randomUUID();

        when(addressRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> addressService.findById(id));
        verify(addressRepo, times(1)).findById(id);
    }

    @Test
    public void testGetAll() {
        List<Address> addresses = List.of(createAddress(), createAddress());
        List<AddressRequestDTO> expectedDTOs = List.of(createAddressRequestDTO(), createAddressRequestDTO());

        when(addressRepo.findAll()).thenReturn(addresses);
        when(addressMapper.mapToAddressRequestDTO(addresses)).thenReturn(expectedDTOs);

        List<AddressRequestDTO> result = addressService.getAll();

        assertNotNull(result);
        assertEquals(expectedDTOs.size(), result.size());
        verify(addressRepo, times(1)).findAll();
        verify(addressMapper, times(1)).mapToAddressRequestDTO(addresses);
    }

    @Test
    public void testSave() {
        Address address = createAddress();
        AddressRequestDTO expectedDTO = createAddressRequestDTO();

        when(addressRepo.save(address)).thenReturn(address);
        when(addressMapper.mapToAddressRequestDTO(address)).thenReturn(expectedDTO);

        AddressRequestDTO result = addressService.save(address);

        assertNotNull(result);
        assertEquals(expectedDTO, result);
        verify(addressRepo, times(1)).save(address);
        verify(addressMapper, times(1)).mapToAddressRequestDTO(address);
    }

    @Test
    public void testDelete() {
        Address address = createAddress();

        when(addressRepo.save(address)).thenReturn(address);

        address = addressRepo.save(address);
        UUID id = address.getId();

        addressService.delete(id);

        verify(addressRepo, times(1)).deleteById(id);
    }
}