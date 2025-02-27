package com.example.aston.controller.attraction;

import com.example.aston.dto.AttractionRequestDTO;
import com.example.aston.model.Attraction;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/attraction")
public interface AttractionController {

    @GetMapping("/all")
    List<AttractionRequestDTO> getAllAttractions();

    @GetMapping("/{id}")
    AttractionRequestDTO getAttractionById(@PathVariable UUID id);

    @PostMapping("/create")
    AttractionRequestDTO createAttraction(@RequestBody Attraction attraction);

    @PutMapping("/{id}")
    AttractionRequestDTO updateAttraction(@PathVariable UUID id, @RequestBody Attraction attraction) ;

    @DeleteMapping("/{id}")
    void deleteAttraction(@PathVariable UUID id);
}