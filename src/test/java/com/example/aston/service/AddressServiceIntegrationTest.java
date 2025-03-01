package com.example.aston.service;

import com.example.aston.dto.AddressRequestDTO;
import com.example.aston.mapper.AddressMapper;
import com.example.aston.model.Address;
import com.example.aston.repository.AddressRepository;
import com.example.aston.service.address.AddressServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;



@Testcontainers
@SpringBootTest
public class AddressServiceIntegrationTest  extends TestContainerConfig{

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
    public void testFindById() {

        Address address = createAddress();
        address = addressRepo.save(address);

        AddressRequestDTO expectedDTO = addressMapper.mapToAddressRequestDTO(address);

        AddressRequestDTO result = addressService.findById(address.getId());

        assertNotNull(result);
        assertEquals(expectedDTO, result);
    }

    @Test
    public void testFindByIdNotFound() {
        UUID id = UUID.randomUUID();

        Exception exception = assertThrows(RuntimeException.class, () -> addressService.findById(id));
        assertEquals("Address not found by id: " + id, exception.getMessage());
    }

    @Test
    public void testGetAll() {
        Address address1 = createAddress();
        Address address2 = createAddress();
        addressRepo.saveAll(List.of(address1, address2));

        List<AddressRequestDTO> expectedDTOs = addressMapper.mapToAddressRequestDTO(List.of(address1, address2));

        List<AddressRequestDTO> result = addressService.getAll();

        assertNotNull(result);
        assertEquals(expectedDTOs.size(), result.size());
    }

    @Test
    public void testSave() {
        Address address = createAddress();

        AddressRequestDTO result = addressService.save(address);

        assertNotNull(result);
        assertEquals(address.getStreet(), result.street());
        assertEquals(address.getCity(), result.city());
    }

    @Test
    public void testDelete() {
        Address address = createAddress();
        address = addressRepo.save(address);

        addressService.delete(address.getId());

        assertFalse(addressRepo.findById(address.getId()).isPresent());
    }
}