package com.example.aston.service.address;


import com.example.aston.dto.AddressRequestDTO;
import com.example.aston.model.Address;

import java.util.List;
import java.util.UUID;


public interface AddressService {

    AddressRequestDTO findById(UUID id);

    List<AddressRequestDTO> getAll();

    AddressRequestDTO save(Address address);

    void delete(UUID id);
}