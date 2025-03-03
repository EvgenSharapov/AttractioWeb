package com.example.aston.controller.attraction;

import com.example.aston.dto.AttractionRequestDTO;
import com.example.aston.handler.exeptions.AddressNotFoundException;
import com.example.aston.model.Address;
import com.example.aston.model.Attraction;
import com.example.aston.repository.AddressRepository;
import com.example.aston.service.address.AddressServiceImpl;
import com.example.aston.service.attraction.AttractionServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AttractionControllerImpl implements AttractionController {

    private final AddressRepository addressRepository;

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
    @ResponseStatus(HttpStatus.CREATED)

    public AttractionRequestDTO createAttraction(@Valid Attraction attraction,@RequestParam UUID addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException(addressId));

        return attractionService.save(attraction,address);
    }

    @Override
    public AttractionRequestDTO updateAttraction(@Valid Attraction attraction, UUID id,UUID addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException(addressId));
        attraction.setId(id);
        attraction.setAddress(address);
        return attractionService.save(attraction,address);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAttraction(UUID id) {
        attractionService.delete(id);

    }

    @Override
    public List<AttractionRequestDTO> findByCity(String city) {
        return attractionService.findByCity(city);
    }

    @Override
    public List<AttractionRequestDTO> findByRegion(String region) {
        return attractionService.findByRegion(region);
    }

    @Override
    public List<AttractionRequestDTO> findByServiceName(String serviceName) {
        return attractionService.findByServiceName(serviceName);
    }

    @Override
    public List<AttractionRequestDTO> findByNameContaining(String name) {
        return attractionService.findByNameContaining(name);
    }


}