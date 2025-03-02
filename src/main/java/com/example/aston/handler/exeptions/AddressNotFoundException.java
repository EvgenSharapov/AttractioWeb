package com.example.aston.handler.exeptions;

import java.util.UUID;

public class AddressNotFoundException extends NotFoundException {
    public AddressNotFoundException(UUID id) {
        super("Address not found by id: " + id);
    }
}