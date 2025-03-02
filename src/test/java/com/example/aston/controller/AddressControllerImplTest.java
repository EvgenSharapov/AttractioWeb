package com.example.aston.controller;

import com.example.aston.controller.address.AddressControllerImpl;
import com.example.aston.dto.AddressRequestDTO;
import com.example.aston.model.Address;
import com.example.aston.service.address.AddressServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AddressControllerImplTest {

    private MockMvc mockMvc;

    @Mock
    private AddressServiceImpl addressService;

    @InjectMocks
    private AddressControllerImpl addressController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();
    }

    private AddressRequestDTO createAddressRequestDTO(){
        return AddressRequestDTO.builder()
                .building(745)
                .street("Angarsk street")
                .city("Moscow")
                .region("Moscow region")
                .build();
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
    void getAllAddresses_ReturnsListOfAddresses() throws Exception {
        AddressRequestDTO addressDTO = createAddressRequestDTO();

        when(addressService.getAll()).thenReturn(Collections.singletonList(addressDTO));

        mockMvc.perform(get("/api/address/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].city").value("Moscow"))
                .andExpect(jsonPath("$[0].building").value(745))
                .andExpect(jsonPath("$[0].street").value("Angarsk street"))
                .andExpect(jsonPath("$[0].region").value("Moscow region"));

        verify(addressService, times(1)).getAll();
    }
    @Test
    void getAddressById_ReturnsAddress() throws Exception {
        UUID id = UUID.randomUUID();
        AddressRequestDTO addressDTO = createAddressRequestDTO();

        when(addressService.findById(id)).thenReturn(addressDTO);

        mockMvc.perform(get("/api/address/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Moscow"))
                .andExpect(jsonPath("$.building").value(745))
                .andExpect(jsonPath("$.street").value("Angarsk street"))
                .andExpect(jsonPath("$.region").value("Moscow region"));

        verify(addressService, times(1)).findById(id);
    }

    @Test
    void createAddress_ReturnsCreatedAddress() throws Exception {
        Address address = createAddress();

        AddressRequestDTO addressDTO = createAddressRequestDTO();

        when(addressService.save(any(Address.class))).thenReturn(addressDTO);

        mockMvc.perform(post("/api/address/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(address)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.city").value("Moscow"))
                .andExpect(jsonPath("$.building").value(745))
                .andExpect(jsonPath("$.street").value("Angarsk street"))
                .andExpect(jsonPath("$.region").value("Moscow region"));

        verify(addressService, times(1)).save(any(Address.class));
    }

    @Test
    void updateAddress_ReturnsUpdatedAddress() throws Exception {
        UUID id = UUID.randomUUID();
        Address address = createAddress();

        AddressRequestDTO addressDTO = createAddressRequestDTO();

        when(addressService.save(any(Address.class))).thenReturn(addressDTO);

        mockMvc.perform(put("/api/address/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(address)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Moscow"))
                .andExpect(jsonPath("$.building").value(745))
                .andExpect(jsonPath("$.street").value("Angarsk street"))
                .andExpect(jsonPath("$.region").value("Moscow region"));

        verify(addressService, times(1)).save(any(Address.class));
    }

    @Test
    void deleteAddress_ReturnsNoContent() throws Exception {
        UUID id = UUID.randomUUID();

        doNothing().when(addressService).delete(id);

        mockMvc.perform(delete("/api/address/{id}", id))
                .andExpect(status().isNoContent());

        verify(addressService, times(1)).delete(id);
    }
}