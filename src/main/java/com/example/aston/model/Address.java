package com.example.aston.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Entity
@Getter
@Setter
@Table(name = "address")
public class Address {
    @Id
    private UUID id;
    private Integer building;
    private String street;
    private String city;
    private String region;


//
//    @OneToMany(mappedBy = "address")
//    private List<Attraction> attractions;

    public Address() {
        this.id = UUID.randomUUID();  // Генерация UUID
    }


}