package com.example.aston.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Getter
@Setter
@Table(name = "ticket_info")
public class TicketInfo {
    @Id
    @Column(name = "ticket_info_id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private BigDecimal price;
    private String currency;
    private Boolean availability;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "attraction_id",foreignKey = @ForeignKey(name = "fk_ticket_attraction"))
    private Attraction attraction;

    public TicketInfo() {

    }
}