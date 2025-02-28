package com.example.aston.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "travel_service_id")
    private UUID id;
    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private ServiceType type;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "attraction_travel_service",
            inverseJoinColumns = @JoinColumn(name = "attraction_id", referencedColumnName = "attraction_id"),
            joinColumns = @JoinColumn(name = "travel_service_id", referencedColumnName = "travel_service_id"))
    private Set<Attraction> attractions;


    public TravelService() {
        this.id = UUID.randomUUID();

    }


}