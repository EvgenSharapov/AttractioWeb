package com.example.aston.model;

import lombok.Getter;

@Getter
public enum ServiceType {
    TOUR_GUIDE("Гид"),
    AUTO_EXCURSION("Авто-экскурсия"),
    MEAL_SERVICE("Питание"),
    ACCOMMODATION("Размещение"),
    TRANSPORTATION("Транспорт"),
    TICKET_SERVICE("Билеты"),
    PHOTOGRAPHY_SERVICE("Услуги фотографа"),
    RENTAL_SERVICE("Аренда"),
    TRAVEL_INSURANCE("Страхование путешествий");

    private final String description;

    ServiceType(String description) {
        this.description = description;
    }

}
