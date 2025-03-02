package com.example.aston.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
@Table(name = "attraction")
public class Attraction {
    @Id
    @Column(name = "attraction_id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name must be less than 100 characters")
    private String name;

    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "Description cannot be blank")
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "The type must be selected")
    private AttractionType type;

    @NotNull(message = "Address cannot be null")
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "address_id", foreignKey = @ForeignKey(name = "fk_attraction_address"))
    private Address address;

    @NotEmpty(message = "Services cannot be empty")
    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "attraction_travel_service",
            joinColumns = @JoinColumn(name = "attraction_id",referencedColumnName = "attraction_id"),
            inverseJoinColumns = @JoinColumn(name = "travel_service_id",referencedColumnName = "travel_service_id"))
    private Set<TravelService> services;

    @Valid
    @OneToOne(mappedBy = "attraction",cascade = CascadeType.ALL, orphanRemoval = true)
    private TicketInfo ticket;




}