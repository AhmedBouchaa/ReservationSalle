package com.mdw3.appgestionprojets.reservationsalle.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;


@Entity
@Data
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Getter @Setter
    private String nom;
    @Getter @Setter
    private String prenom;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String password;
    @Getter @Setter
    private String telephone;
    @Getter @Setter
    @OneToMany(mappedBy = "utilisateur")
    private List<Reservation> reservations;
}

