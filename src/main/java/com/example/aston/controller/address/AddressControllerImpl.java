package com.example.aston.controller.address;

import com.example.aston.dto.AddressRequestDTO;
import com.example.aston.model.Address;
import com.example.aston.service.address.AddressServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AddressControllerImpl implements AddressController{

    private final AddressServiceImpl addressService;


    @Override
    public List<AddressRequestDTO> getAllAddresses() {
        return addressService.getAll();
    }

    @Override
    public AddressRequestDTO getAddressById(@PathVariable UUID id) {
        return addressService.findById(id);
    }

    @Override
    public AddressRequestDTO createAddress(@RequestBody Address address) {
        return addressService.save(address);
    }

    @Override
    public AddressRequestDTO updateAddress(@PathVariable UUID id, @RequestBody Address address) {
        address.setId(id);
        return addressService.save(address);
    }

    @Override
    public void deleteAddress(@PathVariable UUID id) {
        addressService.delete(id);
    }

}