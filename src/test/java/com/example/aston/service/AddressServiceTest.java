package com.example.aston.service;

import com.example.aston.dto.AddressRequestDTO;
import com.example.aston.mapper.AddressMapper;
import com.example.aston.model.Address;
import com.example.aston.repository.AddressRepository;
import com.example.aston.service.address.AddressServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Подключаем Mockito к JUnit
public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepo;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private AddressServiceImpl addressService;

    @Test
    public void testFindById() {
        UUID id = UUID.randomUUID();
        Address address = new Address();

        AddressRequestDTO expectedDTO = AddressRequestDTO.builder()
                .building(745)
                .street("Angarsk street")
                .city("Moscow")
                .region("Moscow region")
                .build();

        when(addressRepo.findById(id)).thenReturn(Optional.of(address));
        when(addressMapper.mapToAddressRequestDTO(address)).thenReturn(expectedDTO);

        AddressRequestDTO result = addressService.findById(id);

        assertNotNull(result);
        assertEquals(expectedDTO, result);
        verify(addressRepo, times(1)).findById(id);
        verify(addressMapper, times(1)).mapToAddressRequestDTO(address);
    }

    @Test
    public void testFindByIdNotFound() {
        UUID id = UUID.randomUUID();

        when(addressRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> addressService.findById(id));
        verify(addressRepo, times(1)).findById(id);
    }

    @Test
    public void testGetAll() {
        List<Address> addresses = List.of(new Address(), new Address());
        AddressRequestDTO expectedDTO1 = AddressRequestDTO.builder()
                .building(745)
                .street("Angarsk street")
                .city("Moscow")
                .region("Moscow region")
                .build();
        AddressRequestDTO expectedDTO2 = AddressRequestDTO.builder()
                .building(745)
                .street("Kirov street")
                .city("Yaroslavl")
                .region("Yaroslavl region")
                .build();

        List<AddressRequestDTO> expectedDTOs = List.of(expectedDTO1, expectedDTO2);

        when(addressRepo.findAll()).thenReturn(addresses);
        when(addressMapper.mapToAddressRequestDTO(addresses)).thenReturn(expectedDTOs);

        List<AddressRequestDTO> result = addressService.getAll();

        assertNotNull(result);
        assertEquals(expectedDTOs.size(), result.size());
        verify(addressRepo, times(1)).findAll();
        verify(addressMapper, times(1)).mapToAddressRequestDTO(addresses);
    }

    @Test
    public void testSave() {
        Address address = new Address();
        AddressRequestDTO expectedDTO = AddressRequestDTO.builder()
                .building(745)
                .street("Angarsk street")
                .city("Moscow")
                .region("Moscow region")
                .build();

        when(addressRepo.save(address)).thenReturn(address);
        when(addressMapper.mapToAddressRequestDTO(address)).thenReturn(expectedDTO);

        AddressRequestDTO result = addressService.save(address);

        assertNotNull(result);
        assertEquals(expectedDTO, result);
        verify(addressRepo, times(1)).save(address);
        verify(addressMapper, times(1)).mapToAddressRequestDTO(address);
    }

    @Test
    public void testDelete() {
        UUID id = UUID.randomUUID();

        doNothing().when(addressRepo).deleteById(id);

        addressService.delete(id);

        verify(addressRepo, times(1)).deleteById(id);
    }
}