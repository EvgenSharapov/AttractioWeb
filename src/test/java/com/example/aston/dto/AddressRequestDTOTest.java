package com.example.aston.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class AddressRequestDTOTest {
    @Test
    public void testAddressRequestDTO() {

        AddressRequestDTO address = AddressRequestDTO.builder()
                .building(745)
                .street("Angarsk street")
                .city("Moscow")
                .region("Moscow region")
                .build();


        assertEquals(745, address.building());
        assertEquals("Angarsk street", address.street());
        assertEquals("Moscow", address.city());
        assertEquals("Moscow region", address.region());
    }


}