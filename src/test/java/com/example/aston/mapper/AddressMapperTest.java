package com.example.aston.mapper;

import com.example.aston.dto.AddressRequestDTO;
import com.example.aston.model.Address;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AddressMapperTest  {

    @InjectMocks
    private AddressMapper addressMapper;

    @Test
    void mapToAddressRequestDTO_ShouldMapSingleAddressCorrectly() {
        Address address = new Address();
        address.setBuilding(745);
        address.setStreet("Angarsk street");
        address.setCity("Moscow");
        address.setRegion("Moscow region");

        AddressRequestDTO result = addressMapper.mapToAddressRequestDTO(address);

        assertNotNull(result);
        assertEquals(745, result.building());
        assertEquals("Angarsk street", result.street());
        assertEquals("Moscow", result.city());
        assertEquals("Moscow region", result.region());
    }

    @Test
    void mapToAddressRequestDTO_ShouldMapListOfAddressesCorrectly() {
        Address address = new Address();
        address.setBuilding(745);
        address.setStreet("Angarsk street");
        address.setCity("Moscow");
        address.setRegion("Moscow region");

        List<Address> addresses = Collections.singletonList(address);

        List<AddressRequestDTO> result = addressMapper.mapToAddressRequestDTO(addresses);

        assertNotNull(result);
        assertEquals(1, result.size());

        AddressRequestDTO dto = result.get(0);
        assertEquals(745, dto.building());
        assertEquals("Angarsk street", dto.street());
        assertEquals("Moscow", dto.city());
        assertEquals("Moscow region", dto.region());
    }

    @Test
    void mapToAddressRequestDTO_ShouldReturnEmptyList_WhenInputListIsEmpty() {
        List<Address> addresses = Collections.emptyList();

        List<AddressRequestDTO> result = addressMapper.mapToAddressRequestDTO(addresses);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}