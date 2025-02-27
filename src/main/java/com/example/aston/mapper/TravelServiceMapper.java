package com.example.aston.mapper;

import com.example.aston.dto.TravelServiceRequestDTO;
import com.example.aston.model.TravelService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TravelServiceMapper {

    public TravelServiceRequestDTO mapToAttractionServiceRequestDTO(TravelService service) {
        return TravelServiceRequestDTO.builder()
                .name(service.getName())
                .description(service.getDescription())
                .type(service.getType())
                .build();
    }
    public List<TravelServiceRequestDTO> mapToAttractionServiceRequestDTO(List<TravelService> services) {
        return services.stream()
                .map(this::mapToAttractionServiceRequestDTO)
                .collect(Collectors.toList());
    }
}
