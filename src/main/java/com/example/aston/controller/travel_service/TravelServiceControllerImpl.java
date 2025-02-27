package com.example.aston.controller.travel_service;

import com.example.aston.dto.TravelServiceRequestDTO;
import com.example.aston.model.TravelService;
import com.example.aston.service.travel_service.TravelServiceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TravelServiceControllerImpl implements TravelServiceController{

    private final TravelServiceServiceImpl travelService;

    @Override
    public List<TravelServiceRequestDTO> getAllTravelServices() {
        return travelService.getAll();
    }

    @Override
    public TravelServiceRequestDTO getTravelServiceById(UUID id) {
        return travelService.findById(id);
    }

    @Override
    public TravelServiceRequestDTO createTravelService(TravelService service) {
        return travelService.save(service);
    }

    @Override
    public TravelServiceRequestDTO updateTravelService(UUID id, TravelService service) {
        service.setId(id);
        return travelService.save(service);
    }

    @Override
    public void deleteTravelService(UUID id) {
        travelService.delete(id);

    }
}
