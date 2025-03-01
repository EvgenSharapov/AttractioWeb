package com.example.aston.dto;

import com.example.aston.model.Address;
import com.example.aston.model.AttractionType;
import com.example.aston.model.TicketInfo;
import lombok.Builder;

@Builder
public record AttractionRequestDTO (
        String name,
        String description,
        AttractionType type,
        TicketInfo ticketInfo
//        Address address
){
}