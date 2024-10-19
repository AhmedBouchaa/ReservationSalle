package com.mdw3.appgestionprojets.reservationsalle.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;

@Entity
@Data
public class Salle {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Getter @Setter private String nom;
    @Getter @Setter private int numero;
    @Getter @Setter private String description  ;
    @Getter @Setter private String type;
    @Getter @Setter private int capacite;
    @Getter @Setter
    @OneToMany(mappedBy = "salle")
    private List<Reservation> reservations;
}
