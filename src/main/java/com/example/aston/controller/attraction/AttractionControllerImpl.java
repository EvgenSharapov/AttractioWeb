package com.example.aston.controller.attraction;

import com.example.aston.dto.AttractionRequestDTO;
import com.example.aston.model.Attraction;
import com.example.aston.service.attraction.AttractionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AttractionControllerImpl implements AttractionController {

    private final AttractionServiceImpl attractionService;


    @Override
    public List<AttractionRequestDTO> getAllAttractions() {
        return attractionService.getAll();
    }

    @Override
    public AttractionRequestDTO getAttractionById(UUID id) {
        return attractionService.findById(id);
    }

    @Override
    public AttractionRequestDTO createAttraction(Attraction attraction) {
        return attractionService.save(attraction);
    }

    @Override
    public AttractionRequestDTO updateAttraction(UUID id, Attraction attraction) {
        attraction.setId(id);
        return attractionService.save(attraction);
    }

    @Override
    public void deleteAttraction(UUID id) {
        attractionService.delete(id);

    }
}