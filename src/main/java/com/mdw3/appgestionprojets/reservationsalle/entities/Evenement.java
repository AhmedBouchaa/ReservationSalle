package com.mdw3.appgestionprojets.reservationsalle.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Entity
@Data
public class Evenement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Getter @Setter private String nom;
    @Getter @Setter private String description;
    @Getter @Setter
    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

}
