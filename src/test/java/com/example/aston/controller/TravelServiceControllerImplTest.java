package com.example.aston.controller;

import com.example.aston.controller.travel_service.TravelServiceControllerImpl;
import com.example.aston.dto.TravelServiceRequestDTO;
import com.example.aston.model.ServiceType;
import com.example.aston.model.TravelService;
import com.example.aston.service.travel_service.TravelServiceServiceImpl;
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

class TravelServiceControllerImplTest {

    private MockMvc mockMvc;

    @Mock
    private TravelServiceServiceImpl travelService;

    @InjectMocks
    private TravelServiceControllerImpl travelServiceController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(travelServiceController).build();
    }

    private TravelServiceRequestDTO createTravelServiceRequestDTO() {
        return TravelServiceRequestDTO.builder()
                .name("Tour Guide")
                .description("Service")
                .type(ServiceType.TOUR_GUIDE)
                .build();
    }


    private TravelService createTravelService() {
        TravelService service = new TravelService();
        service.setName("Tour Guide");
        service.setDescription("Service");
        service.setType(ServiceType.TOUR_GUIDE);
        return service;
    }

    @Test
    void getAllTravelServices_ReturnsListOfServices() throws Exception {
        TravelServiceRequestDTO serviceDTO = createTravelServiceRequestDTO();

        when(travelService.getAll()).thenReturn(Collections.singletonList(serviceDTO));

        mockMvc.perform(get("/api/service/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Tour Guide"))
                .andExpect(jsonPath("$[0].description").value("Service"))
                .andExpect(jsonPath("$[0].type").value("TOUR_GUIDE"));


        verify(travelService, times(1)).getAll();
    }

    @Test
    void getTravelServiceById_ReturnsService() throws Exception {
        UUID id = UUID.randomUUID();
        TravelServiceRequestDTO serviceDTO = createTravelServiceRequestDTO();

        when(travelService.findById(id)).thenReturn(serviceDTO);

        mockMvc.perform(get("/api/service/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tour Guide"))
                .andExpect(jsonPath("$.description").value("Service"))
                .andExpect(jsonPath("$.type").value("TOUR_GUIDE"));

        verify(travelService, times(1)).findById(id);
    }

    @Test
    void createTravelService_ReturnsCreatedService() throws Exception {
        TravelServiceRequestDTO serviceDTO = createTravelServiceRequestDTO();
        TravelService service = createTravelService();

        when(travelService.save(any(TravelService.class))).thenReturn(serviceDTO);

        mockMvc.perform(post("/api/service/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(service)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Tour Guide"))
                .andExpect(jsonPath("$.description").value("Service"))
                .andExpect(jsonPath("$.type").value("TOUR_GUIDE"));

        verify(travelService, times(1)).save(any(TravelService.class));
    }

    @Test
    void updateTravelService_ReturnsUpdatedService() throws Exception {
        UUID id = UUID.randomUUID();
        TravelServiceRequestDTO serviceDTO = createTravelServiceRequestDTO();
        TravelService service = createTravelService();

        when(travelService.save(any(TravelService.class))).thenReturn(serviceDTO);

        mockMvc.perform(put("/api/service/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(service)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tour Guide"))
                .andExpect(jsonPath("$.description").value("Service"))
                .andExpect(jsonPath("$.type").value("TOUR_GUIDE"));

        verify(travelService, times(1)).save(any(TravelService.class));
    }

    @Test
    void deleteTravelService_ReturnsNoContent() throws Exception {
        UUID id = UUID.randomUUID();

        doNothing().when(travelService).delete(id);

        mockMvc.perform(delete("/api/service/{id}", id))
                .andExpect(status().isNoContent());

        verify(travelService, times(1)).delete(id);
    }
}