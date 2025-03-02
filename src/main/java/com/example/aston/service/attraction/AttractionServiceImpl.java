package com.example.aston.service.attraction;

import com.example.aston.dto.AttractionRequestDTO;
import com.example.aston.mapper.AttractionMapper;
import com.example.aston.model.Address;
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
public class AttractionServiceImpl implements AttractionService {

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
        log.debug("Save Attraction: {}", attraction);
        attractionRepo.save(attraction);

        return attractionMapper.mapToAttractionRequestDTO(attraction);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Delete Attraction by id: {}", id);

        attractionRepo.deleteById(id);

    }

    public List<AttractionRequestDTO> findByCity(String city) {
        log.debug("Find Attractions by city: {}", city);

        List<Attraction> attractions = attractionRepo.findByAddress_City(city);

        return attractionMapper.mapToAttractionRequestDTO(attractions);
    }

    public List<AttractionRequestDTO> findByRegion(String region) {
        log.debug("Find Attractions by region: {}", region);

        List<Attraction> attractions = attractionRepo.findByAddress_Region(region);

        return attractionMapper.mapToAttractionRequestDTO(attractions);
    }

    public List<AttractionRequestDTO> findByServiceName(String serviceName) {
        log.debug("Find Attractions by service name: {}", serviceName);

        List<Attraction> attractions = attractionRepo.findByServices_Name(serviceName);

        return attractionMapper.mapToAttractionRequestDTO(attractions);
    }

    public List<AttractionRequestDTO> findByNameContaining(String name) {
        log.debug("Find Attractions by name containing: {}", name);

        List<Attraction> attractions = attractionRepo.findByNameContaining(name);

        return attractionMapper.mapToAttractionRequestDTO(attractions);
    }
}
