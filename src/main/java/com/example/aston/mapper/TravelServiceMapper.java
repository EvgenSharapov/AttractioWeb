package com.example.aston.mapper;

import com.example.aston.dto.TravelServiceRequestDTO;
import com.example.aston.model.TravelService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TravelServiceMapper {

    public TravelServiceRequestDTO mapToTravelServiceRequestDTO(TravelService service) {
        return TravelServiceRequestDTO.builder()
                .name(service.getName())
                .description(service.getDescription())
                .type(service.getType())
                .attractions(service.getAttractions())
                .build();
    }
    public List<TravelServiceRequestDTO> mapToTravelServiceRequestDTO(List<TravelService> services) {
        return services.stream()
                .map(this::mapToTravelServiceRequestDTO)
                .collect(Collectors.toList());
    }
}
