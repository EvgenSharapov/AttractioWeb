package com.example.aston.model;

import lombok.Getter;

@Getter
public enum AttractionType {
    PARK("Парк"),
    MUSEUM("Музей"),
    GALLERY("Галерея"),
    THEATER("Театр"),
    ZOO("Зоопарк"),
    AQUARIUM("Аквариум"),
    AMUSEMENT_PARK("Парк аттракционов"),
    SHOPPING_MALL("Торговый центр"),
    RESTAURANT("Ресторан");

    private final String description;

    AttractionType(String description) {
        this.description = description;
    }


}