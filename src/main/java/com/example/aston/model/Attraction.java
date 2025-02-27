package com.example.aston.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Set;
import java.util.UUID;


@Entity
@Getter
@Setter
@Table(name = "attraction")
public class Attraction {
    @Id
    private UUID id;
    private String name;
    private String description;
//    private String type;

    @Enumerated(EnumType.STRING)
    private AttractionType type;

//    @ManyToOne
//    @JoinColumn(name = "address_id")
//    private Address address;
//
//    @ManyToMany
//    @JoinTable(
//            name = "attraction_service",
//            joinColumns = @JoinColumn(name = "attraction_id",referencedColumnName = "attraction_id"),
//            inverseJoinColumns = @JoinColumn(name = "service_id",referencedColumnName = "service_id"))
//    private Set<AttractionService> services;

//    @OneToOne(mappedBy = "attraction")
//    private TicketInfo ticket



    public Attraction() {
        this.id = UUID.randomUUID();

    }
}