package com.example.aston.service.travel_service;

import com.example.aston.dto.TravelServiceRequestDTO;
import com.example.aston.mapper.TravelServiceMapper;
import com.example.aston.model.TravelService;
import com.example.aston.repository.TravelServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TravelServiceServiceImpl implements TravelServiceService {

    private final TravelServiceRepository serviceRepo;
    private final TravelServiceMapper serviceMapper;



    @Override
    public TravelServiceRequestDTO findById(UUID id) {
        log.debug("Find Travel Service by id: {}", id);

        TravelService service = serviceRepo.findById(id).orElseThrow(
                () ->
                        new RuntimeException("Travel Service not found by id: " + id));
        return serviceMapper.mapToTravelServiceRequestDTO(service);
    }

    @Override
    public List<TravelServiceRequestDTO> getAll() {
        log.debug("Find all Travel Service");

        List<TravelService> services = serviceRepo.findAll();

        return serviceMapper.mapToTravelServiceRequestDTO(services);
    }

    @Override
    public TravelServiceRequestDTO save(TravelService service) {
        log.debug("Save Travel Service: {}",service);
        serviceRepo.save(service);
        return serviceMapper.mapToTravelServiceRequestDTO(service);
    }


    @Override
    public void delete(UUID id) {
        log.debug("Delete Travel Service by id: {}",id);

        serviceRepo.deleteById(id);

    }
}
