package com.example.aston.handler.exeptions;

import java.util.UUID;

public class AttractionNotFoundException extends NotFoundException {
    public AttractionNotFoundException(UUID id) {
        super("Attraction not found by id: " + id);
    }
}