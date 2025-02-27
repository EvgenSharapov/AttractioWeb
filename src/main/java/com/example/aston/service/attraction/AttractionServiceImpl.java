package com.example.aston.service.attraction;

import com.example.aston.dto.AttractionRequestDTO;
import com.example.aston.mapper.AttractionMapper;
import com.example.aston.model.Attraction;
import com.example.aston.repository.AttractionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService{

    private final AttractionRepository attractionRepo;
    private final AttractionMapper attractionMapper;


    @Override
    public AttractionRequestDTO findById(UUID id) {
        log.debug("Find Attraction by id: {}", id);

        Attraction attraction = attractionRepo.findById(id).orElseThrow(
                () ->
                        new RuntimeException("Attraction not found by id: " + id));

        return attractionMapper.mapToAttractionRequestDTO(attraction);
    }

    @Override
    public List<AttractionRequestDTO> getAll() {
        log.debug("Find all Attractions");

        List<Attraction> attractions = attractionRepo.findAll();

        return attractionMapper.mapToAttractionRequestDTO(attractions);
    }

    @Override
    public AttractionRequestDTO save(Attraction attraction) {
        log.debug("Save Attraction: {}",attraction);
        attractionRepo.save(attraction);

        return attractionMapper.mapToAttractionRequestDTO(attraction);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Delete Attraction by id: {}",id);

        attractionRepo.deleteById(id);

    }
}