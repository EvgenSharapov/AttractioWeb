package com.example.aston.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.Set;
import java.util.UUID;


@Entity
@Getter
@Setter
@Table(name = "travel_service")
public class TravelService {
    @Id
    private UUID id;
    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private ServiceType type;

//    @ManyToMany(mappedBy = "services")
//    private Set<Attraction> attractions;

    public TravelService() {
        this.id = UUID.randomUUID();

    }


}