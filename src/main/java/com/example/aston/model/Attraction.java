package com.example.aston.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;


    @Enumerated(EnumType.STRING)
    private AttractionType type;


    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "address_id", foreignKey = @ForeignKey(name = "fk_attraction_address"))
    private Address address;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "attraction_travel_service",
            joinColumns = @JoinColumn(name = "attraction_id",referencedColumnName = "attraction_id"),
            inverseJoinColumns = @JoinColumn(name = "travel_service_id",referencedColumnName = "travel_service_id"))
    private Set<TravelService> services;

    @OneToOne(mappedBy = "attraction",cascade = CascadeType.ALL, orphanRemoval = true)
    private TicketInfo ticket;




}