package com.example.aston.dto;


import com.example.aston.model.Attraction;
import lombok.Builder;

import java.util.List;
import java.util.Set;

@Builder
public record AddressRequestDTO (
        Integer building,
        String street,
        String city,
        String region
//        Set<Attraction> attractionList
){
}