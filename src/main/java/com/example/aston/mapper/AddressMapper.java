package com.example.aston.mapper;

import com.example.aston.dto.AddressRequestDTO;
import com.example.aston.model.Address;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddressMapper {

    public AddressRequestDTO mapToAddressRequestDTO(Address address) {
        return AddressRequestDTO.builder()
                .building(address.getBuilding())
                .street(address.getStreet())
                .city(address.getCity())
                .region(address.getRegion())
                .build();
    }

    public List<AddressRequestDTO> mapToAddressRequestDTO(List<Address> addresses) {
        return addresses.stream()
                .map(this::mapToAddressRequestDTO)
                .collect(Collectors.toList());
    }
}
