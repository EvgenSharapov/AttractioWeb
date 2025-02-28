package com.example.aston.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;


@Entity
@Getter
@Setter
@Table(name = "address")
public class Address {
    @Id
    @Column(name = "address_id")
    private UUID id;
    private Integer building;
    private String street;
    private String city;
    private String region;


//
    @OneToMany(mappedBy = "address")
    @JsonIgnore
    private Set<Attraction> attractions;

    public Address() {
        this.id = UUID.randomUUID();  // Генерация UUID
    }


}