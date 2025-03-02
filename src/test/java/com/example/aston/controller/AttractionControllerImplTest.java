package com.example.aston.controller;

import com.example.aston.controller.attraction.AttractionControllerImpl;
import com.example.aston.dto.AttractionRequestDTO;
import com.example.aston.model.Address;
import com.example.aston.model.Attraction;
import com.example.aston.model.AttractionType;
import com.example.aston.model.TicketInfo;
import com.example.aston.service.attraction.AttractionServiceImpl;
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
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AttractionControllerImplTest {

    private MockMvc mockMvc;

    @Mock
    private AttractionServiceImpl attractionService;

    @InjectMocks
    private AttractionControllerImpl attractionController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(attractionController).build();
    }

    private AttractionRequestDTO createAttractionRequestDTO() {
        return AttractionRequestDTO.builder()
                .name("Attraction")
                .description("Description")
                .type(AttractionType.GALLERY)
                .build();
    }

    private Attraction createAttraction() {
        Attraction attraction = new Attraction();
        attraction.setName("Attraction");
        attraction.setDescription("Description");
        attraction.setType(AttractionType.GALLERY);
        return attraction;
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
        AttractionRequestDTO attractionDTO = createAttractionRequestDTO();
        Attraction attraction = createAttraction();

        when(attractionService.save(any(Attraction.class))).thenReturn(attractionDTO);

        mockMvc.perform(post("/api/attraction/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(attraction)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Attraction"))
                .andExpect(jsonPath("$.description").value("Description"))
                .andExpect(jsonPath("$.type").value("GALLERY"));

        verify(attractionService, times(1)).save(any(Attraction.class));
    }

    @Test
    void updateAttraction_ReturnsUpdatedAttraction() throws Exception {
        UUID id = UUID.randomUUID();
        AttractionRequestDTO attractionDTO = createAttractionRequestDTO();
        Attraction attraction = createAttraction();

        when(attractionService.save(any(Attraction.class))).thenReturn(attractionDTO);

        mockMvc.perform(put("/api/attraction/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(attraction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Attraction"))
                .andExpect(jsonPath("$.description").value("Description"))
                .andExpect(jsonPath("$.type").value("GALLERY"));

        verify(attractionService, times(1)).save(any(Attraction.class));
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