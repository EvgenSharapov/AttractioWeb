package com.example.aston.controller;

import com.example.aston.controller.attraction.AttractionControllerImpl;
import com.example.aston.dto.AttractionRequestDTO;
import com.example.aston.model.Address;
import com.example.aston.model.Attraction;
import com.example.aston.model.AttractionType;
import com.example.aston.repository.AddressRepository;
import com.example.aston.service.attraction.AttractionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = AttractionControllerImpl.class)
class AttractionControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AttractionServiceImpl attractionService;

    @MockitoBean
    private AddressRepository addressRepository;


    @Autowired
    private ObjectMapper objectMapper;


    private AttractionRequestDTO createAttractionRequestDTO() {
        return AttractionRequestDTO.builder()
                .name("Attraction")
                .description("Description")
                .type(AttractionType.GALLERY)
                .build();
    }

    private Attraction createAttraction() {
        Attraction attraction = new Attraction();
        Address address = createAddress();
        attraction.setName("Attraction");
        attraction.setDescription("Description");
        attraction.setType(AttractionType.GALLERY);
        attraction.setAddress(address);
        return attraction;
    }
    private Address createAddress() {
        UUID addressId = UUID.randomUUID();
        Address address = new Address();
        address.setBuilding(745);
        address.setStreet("Angarsk street");
        address.setCity("Moscow");
        address.setRegion("Moscow region");
        address.setId(addressId);
        return address;
    }


    @Test
    void getAllAttractions_ReturnsListOfAttractions() throws Exception {
        AttractionRequestDTO attractionDTO = createAttractionRequestDTO();

        when(attractionService.getAll()).thenReturn(Collections.singletonList(attractionDTO));

        mockMvc.perform(get("/api/attraction/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Attraction"))
                .andExpect(jsonPath("$[0].description").value("Description"))
                .andExpect(jsonPath("$[0].type").value("GALLERY"));

        verify(attractionService, times(1)).getAll();
    }

    @Test
    void getAttractionById_ReturnsAttraction() throws Exception {
        UUID id = UUID.randomUUID();
        AttractionRequestDTO attractionDTO = createAttractionRequestDTO();

        when(attractionService.findById(id)).thenReturn(attractionDTO);

        mockMvc.perform(get("/api/attraction/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Attraction"))
                .andExpect(jsonPath("$.description").value("Description"))
                .andExpect(jsonPath("$.type").value("GALLERY"));

        verify(attractionService, times(1)).findById(id);
    }

    @Test
    void createAttraction_ReturnsCreatedAttraction() throws Exception {
        Address address = createAddress();
        UUID addressId = address.getId();
        Attraction attraction = createAttraction();

        AttractionRequestDTO attractionDTO = createAttractionRequestDTO();

        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        when(attractionService.save(any(Attraction.class),any(Address.class))).thenReturn(attractionDTO);

        mockMvc.perform(post("/api/attraction/create")
                        .param("addressId", addressId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(attraction)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Attraction"))
                .andExpect(jsonPath("$.description").value("Description"))
                .andExpect(jsonPath("$.type").value("GALLERY"));

        verify(addressRepository, times(1)).findById(addressId);
        verify(attractionService, times(1)).save(any(Attraction.class),any(Address.class));
    }

    @Test
    void updateAttraction_ReturnsUpdatedAttraction() throws Exception {
        UUID id = UUID.randomUUID();
        AttractionRequestDTO attractionDTO = createAttractionRequestDTO();
        Attraction attraction = createAttraction();
        Address address = createAddress();
        UUID addressId = address.getId();

        when(attractionService.save(any(Attraction.class),any(Address.class))).thenReturn(attractionDTO);

        mockMvc.perform(put("/api/attraction/{id}/{addressId}", id, addressId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(attraction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Attraction"))
                .andExpect(jsonPath("$.description").value("Description"))
                .andExpect(jsonPath("$.type").value("GALLERY"));

        verify(attractionService, times(1)).save(any(Attraction.class),any(Address.class));
    }

    @Test
    void deleteAttraction_ReturnsNoContent() throws Exception {
        UUID id = UUID.randomUUID();

        doNothing().when(attractionService).delete(id);

        mockMvc.perform(delete("/api/attraction/{id}", id))
                .andExpect(status().isNoContent());

        verify(attractionService, times(1)).delete(id);
    }

}