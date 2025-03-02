package com.example.aston.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


import java.util.Set;
import java.util.UUID;


@Entity
@Getter
@Setter
@Table(name = "travel_service")
public class TravelService {
    @Id
    @Column(name = "travel_service_id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name must be less than 100 characters")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 1000, message = "Description must be less than 1000 characters")
    private String description;

    @NotNull(message = "The type must be selected")
    @Enumerated(EnumType.STRING)
    private ServiceType type;

    @Valid
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