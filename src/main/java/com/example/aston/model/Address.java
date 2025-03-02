package com.example.aston.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Digits(integer = 10000, fraction = 0, message = "Building must be an integer")
    @Min(value = 1, message = "Building must be at least 1")
    private Integer building;

    @NotBlank(message = "Street cannot be blank")
    @Size(max = 200, message = "Street must be less than 200 characters")
    private String street;

    @NotBlank(message = "City cannot be blank")
    @Size(max = 100, message = "City must be less than 100 characters")
    private String city;

    @NotBlank(message = "Region cannot be blank")
    @Size(max = 100, message = "Region must be less than 100 characters")
    private String region;

    @OneToMany(mappedBy = "address")
    @JsonIgnore
    private Set<Attraction> attractions;

    public Address() {

    }


}