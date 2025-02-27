package com.example.aston.controller.travel_service;

import com.example.aston.dto.TravelServiceRequestDTO;
import com.example.aston.model.TravelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/service")
public interface TravelServiceController {

    @GetMapping("/all")
    List<TravelServiceRequestDTO> getAllTravelServices();

    @GetMapping("/{id}")
    TravelServiceRequestDTO getTravelServiceById(@PathVariable UUID id);

    @PostMapping("/create")
    TravelServiceRequestDTO createTravelService(@RequestBody TravelService service);

    @PutMapping("/{id}")
    TravelServiceRequestDTO updateTravelService(@PathVariable UUID id, @RequestBody TravelService service) ;

    @DeleteMapping("/{id}")
    void deleteTravelService(@PathVariable UUID id);
}
