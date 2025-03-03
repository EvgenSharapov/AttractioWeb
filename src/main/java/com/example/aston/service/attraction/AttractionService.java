package com.example.aston.service.attraction;

import com.example.aston.dto.AttractionRequestDTO;
import com.example.aston.model.Address;
import com.example.aston.model.Attraction;

import java.util.List;
import java.util.UUID;

public interface AttractionService {

    AttractionRequestDTO findById(UUID id);

    List<AttractionRequestDTO> getAll();

    AttractionRequestDTO save(Attraction attraction, Address address);

    void delete(UUID id);
}
