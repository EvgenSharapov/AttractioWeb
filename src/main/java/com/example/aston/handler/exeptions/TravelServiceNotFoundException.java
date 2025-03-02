package com.example.aston.handler.exeptions;

import java.util.UUID;

public class TravelServiceNotFoundException extends NotFoundException {
    public TravelServiceNotFoundException(UUID id) {
        super("Travel Service not found by id: " + id);
    }
}