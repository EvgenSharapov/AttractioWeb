package com.example.aston.service.travel_service;

import com.example.aston.dto.TravelServiceRequestDTO;
import com.example.aston.model.TravelService;

import java.util.List;
import java.util.UUID;

public interface TravelServiceService {
    TravelServiceRequestDTO findById(UUID id);

    List<TravelServiceRequestDTO> getAll();

    TravelServiceRequestDTO save(TravelService service);

    void delete(UUID id);
}
