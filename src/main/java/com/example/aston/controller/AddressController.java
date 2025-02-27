package com.example.aston.controller;

import com.example.aston.dto.AddressRequestDTO;
import com.example.aston.model.Address;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/address")
public interface AddressController {

    @GetMapping("/all")
    List<AddressRequestDTO> getAllAddresses();

    @GetMapping("/{id}")
    AddressRequestDTO getAddressById(@PathVariable UUID id);

    @PostMapping("/create")
    AddressRequestDTO createAddress(@RequestBody Address address);

    @PutMapping("/{id}")
    AddressRequestDTO updateAddress(@PathVariable UUID id, @RequestBody Address address) ;

    @DeleteMapping("/{id}")
    void deleteAddress(@PathVariable UUID id);
}