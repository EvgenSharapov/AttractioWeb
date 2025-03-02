package com.example.aston.service.address;

import com.example.aston.dto.AddressRequestDTO;
import com.example.aston.handler.exeptions.AddressNotFoundException;
import com.example.aston.mapper.AddressMapper;
import com.example.aston.model.Address;
import com.example.aston.repository.AddressRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService{

    private final AddressRepository addressRepo;
    private final AddressMapper addressMapper;


    @Override
    public AddressRequestDTO findById(UUID id) {
        log.debug("Find Address by id: {}", id);

        Address address = addressRepo.findById(id).orElseThrow(
                () -> new AddressNotFoundException(id));
        return addressMapper.mapToAddressRequestDTO(address);

    }

    @Override
    public List<AddressRequestDTO> getAll() {
        log.debug("Find all Address");

        List<Address> addresses = addressRepo.findAll();

        return addressMapper.mapToAddressRequestDTO(addresses);

    }

    @Override
    public AddressRequestDTO save(@Valid Address address) {
        log.debug("Save Address: {}",address);
        addressRepo.save(address);

        return addressMapper.mapToAddressRequestDTO(address);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Delete Address by id: {}",id);

        addressRepo.deleteById(id);
    }


}