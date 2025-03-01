package com.example.aston.service;

import com.example.aston.dto.TravelServiceRequestDTO;
import com.example.aston.mapper.TravelServiceMapper;
import com.example.aston.model.Attraction;
import com.example.aston.model.TravelService;
import com.example.aston.model.ServiceType;
import com.example.aston.repository.TravelServiceRepository;
import com.example.aston.service.travel_service.TravelServiceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class TravelServiceServiceTest {

    @Mock
    private TravelServiceRepository serviceRepo;

    @Mock
    private TravelServiceMapper serviceMapper;

    @InjectMocks
    private TravelServiceServiceImpl travelService;

    private TravelService  travelServiceEntity;
    private TravelServiceRequestDTO travelServiceRequestDTO;
    private UUID uuid;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        uuid = UUID.randomUUID();
        travelServiceEntity = new TravelService();

        travelServiceRequestDTO = new TravelServiceRequestDTO("Test name","Test Description", ServiceType.RENTAL_SERVICE);

        when(serviceMapper.mapToTravelServiceRequestDTO(travelServiceEntity)).thenReturn(travelServiceRequestDTO);
    }

    @Test
    void findById_ShouldReturnTravelServiceRequestDTO_WhenServiceExists() {
        when(serviceRepo.findById(uuid)).thenReturn(Optional.of(travelServiceEntity));

        TravelServiceRequestDTO result = travelService.findById(uuid);

        assertNotNull(result);
        assertEquals(travelServiceRequestDTO, result);
        verify(serviceRepo, times(1)).findById(uuid);
    }

    @Test
    void findById_ShouldThrowRuntimeException_WhenServiceDoesNotExist() {
        when(serviceRepo.findById(uuid)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            travelService.findById(uuid);
        });

        assertEquals("Travel Service not found by id: " + uuid, exception.getMessage());
        verify(serviceRepo, times(1)).findById(uuid);
    }

    @Test
    void getAll_ShouldReturnListOfTravelServiceRequestDTO_WhenServicesExist() {
        List<TravelService> services = Collections.singletonList(travelServiceEntity);
        when(serviceRepo.findAll()).thenReturn(services);
        when(serviceMapper.mapToTravelServiceRequestDTO(services)).thenReturn(Collections.singletonList(travelServiceRequestDTO));

        List<TravelServiceRequestDTO> result = travelService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(travelServiceRequestDTO, result.get(0));
        verify(serviceRepo, times(1)).findAll();
    }

    @Test
    void save_ShouldReturnTravelServiceRequestDTO_WhenServiceIsSaved() {
        when(serviceRepo.save(travelServiceEntity)).thenReturn(travelServiceEntity);

        TravelServiceRequestDTO result = travelService.save(travelServiceEntity);

        assertNotNull(result);
        assertEquals(travelServiceRequestDTO, result);
        verify(serviceRepo, times(1)).save(travelServiceEntity);
    }

    @Test
    void delete_ShouldDeleteService_WhenServiceExists() {
        doNothing().when(serviceRepo).deleteById(uuid);

        travelService.delete(uuid);

        verify(serviceRepo, times(1)).deleteById(uuid);
    }
}
